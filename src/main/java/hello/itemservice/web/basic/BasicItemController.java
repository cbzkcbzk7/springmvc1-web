package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * packageName    : hello.itemservice.web.basic
 * fileName       : BasicItemController
 * author         : Sora
 * date           : 2024-05-29
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-29        Sora       최초 생성
 */
@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // @Autowired 생성자 주입과 같음
public class BasicItemController {

    private final ItemRepository itemRepository;


    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/item";
    }


    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    /**
     * @RequestParam 사용법
     */
//    @PostMapping("/add")
    public String addItemV1(@RequestParam("itemName") String itemName,
                            @RequestParam("price") int price,
                            @RequestParam("quantity") Integer quantity,
                            Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);
        model.addAttribute("item", item);

        return "basic/item";
    }

    /**
     * @ModelAttribute 사용법
     *  1. model 객체를 만들어줌
     *  2. model에 값을 넣어줌 ( @ModelAttribute("item") <- 이 아이디로 들어감) : view에서 꺼낼 때 쓰는 이름
     */
  //  @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item){

        itemRepository.save(item);
//        model.addAttribute("item", item); // 자동 추가, 생략 가능

        return "basic/item";
    }


    /**
     * @ModelAttribute 사용법
     *  3. @ModelAttribute("item") 이름 지우면 객체 Item 에서 앞에 소문자로 바꾼 item으로 자동으로 셋팅
     *
     */
//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){

        itemRepository.save(item);
        return "basic/item";
    }

    /**
     * @ModelAttribute 사용법
     *  4. @ModelAttribute 생략 가능
     *
     */
//    @PostMapping("/add")
    public String addItemV4(Item item){

        itemRepository.save(item);
        return "basic/item";
    }

    /**
     * redirect로 변경
     */
//    @PostMapping("/add")
    public String addItemV5(Item item){

        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    /**
     * 클라이언트에 message창 띄워주기
     * redirectAttribute 사용하면 return값에 {itemId} 사용 가능, 남은 redirectAttribute는 쿼리파라미터로 들어가게됨
     * - /basic/items/{itemId}?status=true
     */
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
        Item savedItme = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItme.getId());
        redirectAttributes.addAttribute("status",true);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("{itemId}/edit")
    public String editForm(@PathVariable("itemId") Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    /*
    * return 의 {itemId} @PathVariable이 값 쓰게 해줌
    */
    @PostMapping("{itemId}/edit")
    public String edit(@PathVariable("itemId") Long itemId,@ModelAttribute("item") Item item){

        itemRepository.update(itemId, item);

        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
