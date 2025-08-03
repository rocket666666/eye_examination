#!/bin/bash

# 眼科检查系统停止脚本
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

# 停止服务
stop_service() {
    local service_name=$1
    local pid_file="logs/${service_name}.pid"
    
    if [ -f "$pid_file" ]; then
        local pid=$(cat "$pid_file")
        if kill -0 "$pid" 2>/dev/null; then
            print_info "正在停止 $service_name 服务 (PID: $pid)..."
            kill "$pid"
            
            # 等待进程结束
            for i in {1..10}; do
                if ! kill -0 "$pid" 2>/dev/null; then
                    print_message "$service_name 服务已停止"
                    rm -f "$pid_file"
                    return 0
                fi
                sleep 1
            done
            
            # 强制终止
            print_warning "强制终止 $service_name 服务..."
            kill -9 "$pid" 2>/dev/null
            rm -f "$pid_file"
            print_message "$service_name 服务已强制停止"
        else
            print_warning "$service_name 服务未运行"
            rm -f "$pid_file"
        fi
    else
        print_warning "$service_name 服务PID文件不存在"
    fi
}

# 主程序
main() {
    print_message "=== 正在停止眼科检查系统 ==="
    
    # 停止前端服务
    stop_service "frontend"
    
    # 停止后端服务
    stop_service "backend"
    
    print_message "=== 系统已停止 ==="
}

# 运行主程序
main "$@" 