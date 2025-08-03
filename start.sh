#!/bin/bash

# 眼科检查系统启动脚本
# 作者: AI Assistant
# 日期: 2024-01-15

# 设置颜色
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 打印带颜色的消息
print_message() {
    echo -e "${GREEN}[$(date '+%Y-%m-%d %H:%M:%S')] $1${NC}"
}

print_error() {
    echo -e "${RED}[$(date '+%Y-%m-%d %H:%M:%S')] ERROR: $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}[$(date '+%Y-%m-%d %H:%M:%S')] WARNING: $1${NC}"
}

print_info() {
    echo -e "${BLUE}[$(date '+%Y-%m-%d %H:%M:%S')] INFO: $1${NC}"
}

# 检查命令是否存在
check_command() {
    if ! command -v $1 &> /dev/null; then
        print_error "$1 命令不存在，请先安装 $1"
        exit 1
    fi
}

# 检查Java版本
check_java() {
    if java -version 2>&1 | grep -q "version \"1[1-9]"; then
        print_message "Java 版本检查通过"
    else
        print_error "需要 Java 11 或更高版本"
        exit 1
    fi
}

# 检查Node版本
check_node() {
    if node --version | grep -q "v1[6-9]\|v[2-9][0-9]"; then
        print_message "Node.js 版本检查通过"
    else
        print_error "需要 Node.js 16 或更高版本"
        exit 1
    fi
}

# 检查MySQL是否运行
check_mysql() {
    if command -v mysql &> /dev/null; then
        if mysql -h localhost -u eye_examination -peye_examination -e "SELECT 1" &> /dev/null; then
            print_message "MySQL 连接正常"
        else
            print_warning "MySQL 连接失败，请检查配置"
        fi
    else
        print_warning "MySQL 客户端未安装"
    fi
}

# 检查Redis是否运行
check_redis() {
    if command -v redis-cli &> /dev/null; then
        if redis-cli ping &> /dev/null; then
            print_message "Redis 连接正常"
        else
            print_warning "Redis 连接失败，请检查服务"
        fi
    else
        print_warning "Redis 客户端未安装"
    fi
}

# 启动后端服务
start_backend() {
    print_message "正在启动后端服务..."
    
    cd backend
    
    # 检查是否已编译
    if [ ! -f "target/eye-examination-backend-1.0.0.jar" ]; then
        print_info "首次启动，正在编译后端..."
        if mvn clean package -DskipTests; then
            print_message "后端编译成功"
        else
            print_error "后端编译失败"
            exit 1
        fi
    fi
    
    # 启动Spring Boot应用
    print_info "启动Spring Boot应用..."
    nohup java -jar target/eye-examination-backend-1.0.0.jar > ../logs/backend.log 2>&1 &
    BACKEND_PID=$!
    echo $BACKEND_PID > ../logs/backend.pid
    
    cd ..
    
    # 等待后端启动
    print_info "等待后端服务启动..."
    for i in {1..30}; do
        if curl -s http://localhost:8080/api/actuator/health &> /dev/null; then
            print_message "后端服务启动成功 (PID: $BACKEND_PID)"
            return 0
        fi
        sleep 2
    done
    
    print_error "后端服务启动超时"
    return 1
}

# 启动前端服务
start_frontend() {
    print_message "正在启动前端服务..."
    
    cd frontend
    
    # 检查是否已安装依赖
    if [ ! -d "node_modules" ]; then
        print_info "首次启动，正在安装前端依赖..."
        if npm install; then
            print_message "前端依赖安装成功"
        else
            print_error "前端依赖安装失败"
            exit 1
        fi
    fi
    
    # 启动开发服务器
    print_info "启动前端开发服务器..."
    nohup npm run dev > ../logs/frontend.log 2>&1 &
    FRONTEND_PID=$!
    echo $FRONTEND_PID > ../logs/frontend.pid
    
    cd ..
    
    # 等待前端启动
    print_info "等待前端服务启动..."
    for i in {1..20}; do
        if curl -s http://localhost:3000 &> /dev/null; then
            print_message "前端服务启动成功 (PID: $FRONTEND_PID)"
            return 0
        fi
        sleep 2
    done
    
    print_error "前端服务启动超时"
    return 1
}

# 创建日志目录
mkdir -p logs

# 主程序
main() {
    print_message "=== 眼科检查系统启动 ==="
    
    # 环境检查
    print_info "正在检查系统环境..."
    check_command "java"
    check_command "mvn"
    check_command "node"
    check_command "npm"
    check_java
    check_node
    check_mysql
    check_redis
    
    # 启动服务
    print_info "正在启动服务..."
    
    # 启动后端
    if start_backend; then
        print_message "后端服务启动成功"
    else
        print_error "后端服务启动失败"
        exit 1
    fi
    
    # 启动前端
    if start_frontend; then
        print_message "前端服务启动成功"
    else
        print_error "前端服务启动失败"
        exit 1
    fi
    
    print_message "=== 系统启动完成 ==="
    print_info "访问地址:"
    print_info "  前端: http://localhost:3000"
    print_info "  后端API: http://localhost:8080/api"
    print_info "  业务登录: http://localhost:3000/login"
    print_info "  管理员登录: http://localhost:3000/admin-login"
    print_info ""
    print_info "默认账号:"
    print_info "  管理员: admin / admin123"
    print_info "  医生: doctor / admin123"
    print_info "  护士: nurse / admin123"
    print_info "  技师: technician / admin123"
    print_info ""
    print_info "日志文件:"
    print_info "  后端: logs/backend.log"
    print_info "  前端: logs/frontend.log"
    print_info ""
    print_info "停止服务: ./stop.sh"
    print_info "查看状态: ./status.sh"
}

# 运行主程序
main "$@" 