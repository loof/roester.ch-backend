package ch.roester.tag;

import ch.roester.product.ProductMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface TagMapper {


//    @Mapping(target = "coffee_ids", source = "coffees", qualifiedByName = "coffeesToCoffeeIds")
//    TypeDto toDto(Type entity);
//
//    @Named("coffeesToCoffeeIds")
//    default List<Integer> coffeesToCoffeeIds(Set<Coffee> coffees) {
//        List<Integer> coffeeIds = new ArrayList<>();
//        for (Coffee coffee : coffees) {
//            if (coffee.getId() != null) {
//                coffeeIds.add(coffee.getId());
//            }
//        }
//        return coffeeIds;
//    }
//
//
//    @Mapping(target = "coffees", source = "coffee_ids", qualifiedByName = "coffeeIdsToCoffees")
//    Type toEntity(TypeDto productDTO);
//
//    @Named("coffeeIdsToCoffees")
//    default Set<Coffee> coffeeIdsToCoffees(List<Integer> coffeeIds) {
//        Set<Coffee> coffees = new HashSet<>();
//        for (Integer coffeeId : coffeeIds) {
//            Coffee coffee = new Coffee();
//            coffee.setId(coffeeId);
//            coffees.add(coffee);
//        }
//        return coffees;
//    }


}