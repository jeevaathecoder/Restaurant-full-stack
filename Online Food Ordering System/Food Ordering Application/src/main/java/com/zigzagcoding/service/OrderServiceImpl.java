package com.zigzagcoding.service;

import com.zigzagcoding.model.*;
import com.zigzagcoding.repository.AddressRepository;
import com.zigzagcoding.repository.OrderItemRepostiory;
import com.zigzagcoding.repository.OrderRepository;
import com.zigzagcoding.repository.UserRepository;
import com.zigzagcoding.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepostiory orderItemRepostiory;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RestaurantService restaurantService;


    @Autowired
    private CartService cartService;


    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {

        Address shippingAddres = order.getDeliveryAddress();

        Address savedAddress = addressRepository.save(shippingAddres);

        if(!user.getAddresses().contains(savedAddress)){
           user.getAddresses().add(savedAddress);
           userRepository.save(user);
        }

        Restaurant restaurant = restaurantService.findRestaurantById(order.getRestaurantId());

        Order createdOrder  = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setRestaurant(restaurant);
        createdOrder.setDeliveryAddress(savedAddress);

        Cart cart = cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem : cart.getItem()){
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients() );
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepostiory.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        Long totalPrice = cartService.calucateCartTotals(cart);
        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(createdOrder);
        restaurant.getOrders().add(savedOrder);

        return createdOrder;

    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        if(orderStatus.equals("OUT_FOR_DELIVERY") ||
                orderStatus.equals("DELIVERED") ||
                orderStatus.equals("COMPLETED") ||
                orderStatus.equals("PENDING"))
        {
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }

        throw  new Exception("Please Select a valid order Status");
    }


    @Override
    public void cancelOrder(Long orderId) throws Exception {

       Order order = findOrderById(orderId);
       orderRepository.deleteById(orderId);
    }


    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {
       return orderRepository.findByCustomerId(userId);
    }



    @Override
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders =  orderRepository.findByRestaurantId(restaurantId);

        if(orderStatus !=null){
            orders = orders.stream().filter(order ->
                    order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());

        }

        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isEmpty()){
            throw new Exception("Order not found");

        }
        return optionalOrder.get();
    }
}
