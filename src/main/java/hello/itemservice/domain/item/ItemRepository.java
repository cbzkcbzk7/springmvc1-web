package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : hello.itemservice.domain.item
 * fileName       : ItemRepository
 * author         : Sora
 * date           : 2024-05-29
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-29        Sora       최초 생성
 */
@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); //static , 실무는 HashMap() 쓰면 안됨
    private static Long sequence = 0L; // static, 실무는 Long 쓰면 안된다 : 둘 다 멀티 쓰레드때문

    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id){
        return store.get(id);
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    /*
     * 실무에서 id가 안들어가므로 3개 변수의 DTO를 따로 만들어 주는게 나음
     * 중복인거 같아도 명확성에 더 신경쓰는 것이 좋다
     */
    public void update(Long itemId, Item updateParam){
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore(){
        store.clear();
    }

}
