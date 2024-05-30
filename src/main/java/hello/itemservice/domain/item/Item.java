package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : hello.itemservice.domain.item
 * fileName       : Item
 * author         : Sora
 * date           : 2024-05-29
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-29        Sora       최초 생성
 */
@Data // Data는 위험해서 실무에서는 @Getter @Setter로 쓰는게 좋다
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item(){}
    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
