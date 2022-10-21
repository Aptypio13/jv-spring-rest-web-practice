package mate.academy.spring.controller;

import mate.academy.spring.mapper.DtoResponseMapper;
import mate.academy.spring.model.ShoppingCart;
import mate.academy.spring.model.dto.response.ShoppingCartResponseDto;
import mate.academy.spring.service.MovieSessionService;
import mate.academy.spring.service.ShoppingCartService;
import mate.academy.spring.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final DtoResponseMapper<ShoppingCartResponseDto, ShoppingCart>
            shoppingCartResponseMapping;
    private final MovieSessionService movieSessionService;
    private final UserService userService;

    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  DtoResponseMapper<ShoppingCartResponseDto,
                                          ShoppingCart> shoppingCartResponseMapping,
                                  MovieSessionService movieSessionService,
                                  UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.shoppingCartResponseMapping = shoppingCartResponseMapping;
        this.movieSessionService = movieSessionService;
        this.userService = userService;
    }

    @PutMapping("/movie-sessions")
    public ShoppingCartResponseDto addMovieSessionInShoppingCart(
            @RequestParam Long userId, @RequestParam Long movieId) {
        shoppingCartService.addSession(
                movieSessionService.get(movieId), userService.get(userId));
        return shoppingCartResponseMapping.toDto(
                shoppingCartService.getByUser(userService.get(userId)));
    }

    @GetMapping("/by-user")
    public ShoppingCartResponseDto getShoppingCartByUser(@RequestParam Long userId) {
        return shoppingCartResponseMapping.toDto(
                shoppingCartService.getByUser(userService.get(userId)));
    }

}
