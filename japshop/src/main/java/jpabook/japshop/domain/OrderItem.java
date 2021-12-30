package jpabook.japshop.domain;

import jpabook.japshop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문수량

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem order = new OrderItem();
        order.setItem(item);
        order.setOrderPrice(orderPrice);
        order.setCount(count);

        item.removeStock(count);
        return order;
    }

    //==비지니스 로직==//
    public void cancel() {
        getItem().addStock(count);
    }

    //==조회 로직==//
    public int getTotalPrice() { return getOrderPrice() * getCount(); }
}
