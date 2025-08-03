#!/bin/bash

# 眼科检查系统状态查看脚本
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
    echo -e "${GREEN}$1${NC}"
}

print_error() {
    echo -e "${RED}$1${NC}"
}

print_warning() {
    echo -e "${YELLOW}$1${NC}"
}

print_info() {
    echo -e "${BLUE}$1${NC}"
}

# 检查服务状态
check_service_status() {
    local service_name=$1
    local port=$2
    local pid_file="logs/${service_name}.pid"
    
    printf "%-10s: " "$service_name"
    
    if [ -f "$pid_file" ]; then
        local pid=$(cat "$pid_file")
        if kill -0 "$pid" 2>/dev/null; then
            if curl -s "http://localhost:$port" &> /dev/null; then
                print_message "运行中 (PID: $pid, 端口: $port)"
            else
                print_warning "进程存在但服务不可用 (PID: $pid)"
            fi
        else
            print_error "进程已停止"
            rm -f "$pid_file"
        fi
    else
        print_error "未运行"
    fi
}

# 检查数据库状态
check_database_status() {
    printf "%-10s: " "MySQL"
    
    if command -v mysql &> /dev/null; then
        if mysql -h localhost -u root -p123456 -e "SELECT 1" &> /dev/null; then
            print_message "运行中 (端口: 3306)"
        else
            print_error "连接失败"
        fi
    else
        print_error "客户端未安装"
    fi
    
    printf "%-10s: " "Redis"
    
    if command -v redis-cli &> /dev/null; then
        if redis-cli ping &> /dev/null; then
            print_message "运行中 (端口: 6379)"
        else
            print_error "连接失败"
        fi
    else
        print_error "客户端未安装"
    fi
}

# 显示系统信息
show_system_info() {
    echo
    print_info "=== 系统信息 ==="
    echo "系统时间: $(date '+%Y-%m-%d %H:%M:%S')"
    echo "运行时长: $(uptime | awk '{print $3,$4}' | sed 's/,//')"
    echo "内存使用: $(free -h | awk '/^Mem:/ {print $3 "/" $2}')"
    echo "磁盘使用: $(df -h . | awk 'NR==2 {print $3 "/" $2 " (" $5 ")"}')"
}

# 显示日志文件信息
show_log_info() {
    echo
    print_info "=== 日志文件 ==="
    
    if [ -f "logs/backend.log" ]; then
        local backend_size=$(du -h logs/backend.log | awk '{print $1}')
        local backend_lines=$(wc -l < logs/backend.log)
        echo "后端日志: logs/backend.log (${backend_size}, ${backend_lines} 行)"
    else
        echo "后端日志: 不存在"
    fi
    
    if [ -f "logs/frontend.log" ]; then
        local frontend_size=$(du -h logs/frontend.log | awk '{print $1}')
        local frontend_lines=$(wc -l < logs/frontend.log)
        echo "前端日志: logs/frontend.log (${frontend_size}, ${frontend_lines} 行)"
    else
        echo "前端日志: 不存在"
    fi
}

# 显示访问地址
show_access_info() {
    echo
    print_info "=== 访问地址 ==="
    echo "前端页面: http://localhost:3000"
    echo "后端API: http://localhost:8080/api"
    echo "业务登录: http://localhost:3000/login"
    echo "管理员登录: http://localhost:3000/admin-login"
}

# 主程序
main() {
    print_message "=== 眼科检查系统状态 ==="
    echo
    
    print_info "=== 服务状态 ==="
    check_service_status "前端" "3000"
    check_service_status "后端" "8080"
    
    echo
    print_info "=== 数据库状态 ==="
    check_database_status
    
    show_system_info
    show_log_info
    show_access_info
    
    echo
    print_info "=== 常用命令 ==="
    echo "启动系统: ./start.sh"
    echo "停止系统: ./stop.sh"
    echo "查看后端日志: tail -f logs/backend.log"
    echo "查看前端日志: tail -f logs/frontend.log"
    echo "重启系统: ./stop.sh && ./start.sh"
}

# 运行主程序
main "$@" 