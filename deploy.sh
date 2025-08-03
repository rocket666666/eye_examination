#!/bin/bash

# 眼科检查系统一键部署脚本
# Eye Examination System Deployment Script

echo "=========================================="
echo "眼科检查系统 Docker 部署脚本"
echo "Eye Examination System Docker Deployment"
echo "=========================================="

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 检查Docker是否安装
check_docker() {
    if ! command -v docker &> /dev/null; then
        echo -e "${RED}错误: Docker未安装，请先安装Docker${NC}"
        echo -e "${YELLOW}请访问: https://docs.docker.com/get-docker/${NC}"
        exit 1
    fi
    echo -e "${GREEN}✓ Docker已安装${NC}"
}

# 检查Docker Compose是否安装
check_docker_compose() {
    if ! command -v docker-compose &> /dev/null; then
        echo -e "${RED}错误: Docker Compose未安装，请先安装Docker Compose${NC}"
        echo -e "${YELLOW}请访问: https://docs.docker.com/compose/install/${NC}"
        exit 1
    fi
    echo -e "${GREEN}✓ Docker Compose已安装${NC}"
}

# 检查端口是否被占用
check_ports() {
    local ports=("80" "3306" "6379" "8080")
    for port in "${ports[@]}"; do
        if lsof -Pi :$port -sTCP:LISTEN -t >/dev/null 2>&1; then
            echo -e "${RED}错误: 端口 $port 已被占用${NC}"
            echo -e "${YELLOW}请停止占用该端口的服务后重试${NC}"
            exit 1
        fi
    done
    echo -e "${GREEN}✓ 所有端口检查通过${NC}"
}

# 创建必要的目录
create_directories() {
    echo -e "${BLUE}创建必要的目录...${NC}"
    mkdir -p docker/mysql docker/redis docker/nginx
    echo -e "${GREEN}✓ 目录创建完成${NC}"
}

# 显示系统信息
show_system_info() {
    echo -e "${BLUE}系统信息:${NC}"
    echo "操作系统: $(uname -s)"
    echo "Docker版本: $(docker --version)"
    echo "Docker Compose版本: $(docker-compose --version)"
    echo ""
}

# 部署服务
deploy_services() {
    echo -e "${BLUE}开始部署服务...${NC}"
    
    # 停止可能存在的容器
    echo -e "${YELLOW}停止现有容器...${NC}"
    docker-compose down 2>/dev/null || true
    
    # 构建并启动服务
    echo -e "${YELLOW}构建并启动服务...${NC}"
    docker-compose up -d --build
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓ 服务启动成功${NC}"
    else
        echo -e "${RED}✗ 服务启动失败${NC}"
        exit 1
    fi
}

# 等待服务启动
wait_for_services() {
    echo -e "${BLUE}等待服务启动...${NC}"
    
    # 等待MySQL启动
    echo -e "${YELLOW}等待MySQL启动...${NC}"
    timeout=60
    while [ $timeout -gt 0 ]; do
        if docker-compose exec -T mysql mysqladmin ping -h"localhost" --silent 2>/dev/null; then
            echo -e "${GREEN}✓ MySQL已启动${NC}"
            break
        fi
        sleep 2
        timeout=$((timeout-2))
    done
    
    # 等待Redis启动
    echo -e "${YELLOW}等待Redis启动...${NC}"
    timeout=30
    while [ $timeout -gt 0 ]; do
        if docker-compose exec -T redis redis-cli ping 2>/dev/null | grep -q "PONG"; then
            echo -e "${GREEN}✓ Redis已启动${NC}"
            break
        fi
        sleep 2
        timeout=$((timeout-2))
    done
    
    # 等待后端服务启动
    echo -e "${YELLOW}等待后端服务启动...${NC}"
    timeout=120
    while [ $timeout -gt 0 ]; do
        if curl -f http://localhost:8080/api/health 2>/dev/null; then
            echo -e "${GREEN}✓ 后端服务已启动${NC}"
            break
        fi
        sleep 3
        timeout=$((timeout-3))
    done
    
    # 等待前端服务启动
    echo -e "${YELLOW}等待前端服务启动...${NC}"
    timeout=60
    while [ $timeout -gt 0 ]; do
        if curl -f http://localhost 2>/dev/null; then
            echo -e "${GREEN}✓ 前端服务已启动${NC}"
            break
        fi
        sleep 2
        timeout=$((timeout-2))
    done
}

# 显示部署结果
show_deployment_result() {
    echo ""
    echo "=========================================="
    echo -e "${GREEN}部署完成！${NC}"
    echo "=========================================="
    echo ""
    echo -e "${BLUE}服务访问地址:${NC}"
    echo -e "前端应用: ${GREEN}http://localhost${NC}"
    echo -e "后端API: ${GREEN}http://localhost:8080/api${NC}"
    echo -e "API文档: ${GREEN}http://localhost:8080/api/swagger-ui.html${NC}"
    echo -e "MySQL: ${GREEN}localhost:3306${NC}"
    echo -e "Redis: ${GREEN}localhost:6379${NC}"
    echo ""
    echo -e "${BLUE}默认账户:${NC}"
    echo -e "管理员: ${GREEN}admin / 123456${NC}"
    echo -e "医生: ${GREEN}doctor / 123456${NC}"
    echo -e "护士: ${GREEN}nurse / 123456${NC}"
    echo -e "技师: ${GREEN}technician / 123456${NC}"
    echo ""
    echo -e "${BLUE}数据库配置:${NC}"
    echo -e "数据库名: ${GREEN}eye_examination${NC}"
    echo -e "用户名: ${GREEN}eye_examination${NC}"
    echo -e "密码: ${GREEN}eye_examination${NC}"
    echo ""
    echo -e "${YELLOW}常用命令:${NC}"
    echo "查看服务状态: docker-compose ps"
    echo "查看日志: docker-compose logs -f"
    echo "停止服务: docker-compose down"
    echo "重启服务: docker-compose restart"
    echo ""
}

# 显示服务状态
show_service_status() {
    echo -e "${BLUE}服务状态:${NC}"
    docker-compose ps
    echo ""
}

# 主函数
main() {
    echo -e "${BLUE}开始检查系统环境...${NC}"
    
    # 检查系统环境
    check_docker
    check_docker_compose
    check_ports
    
    # 显示系统信息
    show_system_info
    
    # 创建目录
    create_directories
    
    # 部署服务
    deploy_services
    
    # 等待服务启动
    wait_for_services
    
    # 显示服务状态
    show_service_status
    
    # 显示部署结果
    show_deployment_result
}

# 异常处理
trap 'echo -e "${RED}部署过程中发生错误，正在清理...${NC}"; docker-compose down 2>/dev/null || true; exit 1' ERR

# 执行主函数
main

echo -e "${GREEN}🎉 眼科检查系统部署完成！${NC}" 