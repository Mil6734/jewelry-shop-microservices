package org.jewelry.jewelryshop.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.jewelry.jewelryshop.orderservice.client.CatalogClient;
import org.jewelry.jewelryshop.orderservice.client.UserClient;
import org.jewelry.jewelryshop.orderservice.dto.*;
import org.jewelry.jewelryshop.orderservice.mapper.OrderMapper;
import org.jewelry.jewelryshop.orderservice.model.Order;
import org.jewelry.jewelryshop.orderservice.model.OrderItem;
import org.jewelry.jewelryshop.orderservice.model.OrderStatus;
import org.jewelry.jewelryshop.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Реализация бизнес-логики работы с заказами
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    @Qualifier("orderMapperImpl")
    private final OrderMapper mapper;
    private final CatalogClient catalogClient;
    private final UserClient userClient;

    @Override
    @Transactional
    public UUID createOrder(UUID userId, OrderRequestDTO request) {
        try {
            UserInfoDTO user = userClient.getUser(userId);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден: " + userId);
        }

        Order order = mapper.toEntity(request);
        order.setUserId(userId);
        order.setStatus(OrderStatus.NEW);

        Set<UUID> ids = request.getItems()
                .stream()
                .map(OrderItemDTO::getProductId)
                .collect(Collectors.toSet());

        List<ProductInfoDTO> products = catalogClient.getProducts(ids);

        BigDecimal total = BigDecimal.ZERO;
        Map<UUID, ProductInfoDTO> infoMap = products.stream()
                .collect(Collectors.toMap(ProductInfoDTO::getId, p -> p));

        for (OrderItemDTO dto : request.getItems()) {
            ProductInfoDTO info = infoMap.get(dto.getProductId());
            if (info == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден: " + dto.getProductId());
            }

            OrderItem item = mapper.toEntity(dto);
            item.setOrder(order);
            item.setPrice(info.getPrice());
            order.getItems().add(item);

            total = total.add(info.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())));
        }
        order.setTotalPrice(total);

        Order saved = orderRepository.save(order);
        return saved.getId();
    }

    @Override
    @Transactional
    public OrderResponseDTO getOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Заказ не найден: " + orderId));
        return mapper.toDto(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> getOrdersByUser(UUID userId, Pageable pageable) {
        Order probe = Order.builder().userId(userId).build();
        Page<Order> page = orderRepository.findAll(Example.of(probe), pageable);
        return page.map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> getAllOrders(Pageable pageable) {
        Page<Order> page = orderRepository.findAll(pageable);
        return page.map(mapper::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateStatus(UUID orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Заказ не найден: " + orderId));
        order.setStatus(newStatus);
        orderRepository.save(order);
    }
}

