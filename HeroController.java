package br.com.brainweb.interview.core.features.hero;
​
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
​
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
​
import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/api/hero", produces = APPLICATION_JSON_VALUE)
public class HeroController {
    @Autowired
    private HeroService heroService;
    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Validated
                                    @RequestBody CreateHeroRequest createHeroRequest) {
        final UUID heroiId = heroService.create(createHeroRequest);
        return ResponseEntity.ok(heroiId);
    }
    @GetMapping(value = "/all", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Hero>> getAll() {
        return ResponseEntity.ok(heroService.getAll());
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@RequestParam UUID id) {
        //busca heroi pelo id
        Hero hero = heroService.getById(id);
​
        //se nao for nulo deleta
        if(hero != null) {
            heroService.delete(hero.getId());
        } else {
            //se for nulo retorna erro
            return ResponseEntity.ok("Nao encontrei o id: " + id);
        }
        return ResponseEntity.ok("Excluido com sucesso");
    }
​
}
