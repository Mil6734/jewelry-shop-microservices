# тест, пока что catalog-service
.PHONY: port-forward dev-up dev-down restart-service

# пробрасываем порты для сервисов
port-forward:
	@echo "catalog-service → http://localhost:8081"
	@kubectl port-forward service/catalog-service 8081:8081 &

	@echo "Grafana → http://localhost:3000"
	@kubectl port-forward -n monitoring svc/monitoring-stack-grafana 3000:80 &

	@echo "Prometheus → http://localhost:9090"
	@kubectl port-forward -n monitoring svc/monitoring-stack-kube-prom-prometheus 9090:9090 &

# полный запуск окружения
dev-up:
	@echo "Запуск Minikube и манифестов"
	minikube start
	kubectl apply -f k8s/catalog-service/
	kubectl apply -f k8s/postgresql/
	make port-forward

# Остановка проброшенных портов
dev-down:
	@echo "Завершение все kubectl port-forward процессы"
	@pkill -f "kubectl port-forward" || true

# Перезапуск catalog-service
restart-service:
	@echo "Перезапускаем catalog-service"
	kubectl rollout restart deployment catalog-service