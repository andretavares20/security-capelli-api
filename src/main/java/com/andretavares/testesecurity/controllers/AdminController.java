package com.andretavares.testesecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.andretavares.testesecurity.dto.CategoriaDto;
import com.andretavares.testesecurity.dto.CorDto;
import com.andretavares.testesecurity.dto.OrdemRequest;
import com.andretavares.testesecurity.dto.OrdemResponse;
import com.andretavares.testesecurity.dto.ProdutoDto;
import com.andretavares.testesecurity.entities.Categoria;
import com.andretavares.testesecurity.entities.Cor;
import com.andretavares.testesecurity.entities.Ordem;
import com.andretavares.testesecurity.entities.Produto;
import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.services.CategoriaService;
import com.andretavares.testesecurity.services.CorService;
import com.andretavares.testesecurity.services.OrdemService;
import com.andretavares.testesecurity.services.ProdutoService;
import com.andretavares.testesecurity.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrdemService ordemService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CorService corService;

    @Operation(summary = "Cria um produto", description = "Envie para esse endpoint o JSON contendo os dados do produto e imagens")
    @PostMapping("/produto")
    public ResponseEntity<Object> create(@RequestBody ProdutoDto produtoDto) {

        return ResponseEntity.ok().body(produtoService.create(produtoDto));

    }

    @Operation(summary = "Atualiza um produto", description = "Envie para esse endpoint o json contendo todo objeto e o id,mude apenas os dados que queira atualizar.")
    @PutMapping("/produto")
    public ResponseEntity<Produto> edit(@RequestBody Produto produto) {

        return ResponseEntity.ok().body(produtoService.edit(produto));

    }

    @Operation(summary = "Remove um produto", description = "Envie para esse endpoint o id do produto que deseja deletar")
    @DeleteMapping("/produto/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        produtoService.deleteById(id);
    }

    @Operation(summary  = "Retorna todos os usuários do sistema.")
    @GetMapping("/users")
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok().body(userService.findAll());
    }

    @Operation(summary  = "Deleta usuário pelo id.", description  = "Você precisa enviar o id do usuário.")
    @DeleteMapping("/user/{id}")
    public void deleteUserById(@PathVariable("id") Long id){
        userService.deleteById(id);
    }

    @Operation(summary = "Adiciona imagens a um produto", description = "Envie para esse endpoint o JSON contendo os dados do produto e imagens")
    @PostMapping(value = "/adicionar-imagens", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Object> addImagens(@RequestParam Long idProduto,
            @RequestPart("files") List<MultipartFile> files) {

        return ResponseEntity.ok().body(produtoService.addImagens(idProduto, files));
        
    }

    @Operation(summary  = "Finaliza compra no carrinho e gera um ordem de compra em RASCUNHO", description  = "Envie para esse endpoint o id do usuário que ta realizando a compra, o json do objeto Ordem e os itens contendo o id do produto.")
    @PostMapping("/ordens")
    public ResponseEntity<OrdemResponse> create(Long userId, @RequestBody OrdemRequest request) {

        return ResponseEntity.ok().body(ordemService.create(userId, request));

    }

    @Operation(summary  = "Cancela ordem de serviço e atualiza o status para CANCELADO", description  = "Envie para esse endpoint o id do usuário que ta cancelando a ordem e o id da ordem")
    @PutMapping("/ordens/{ordemId}/cancel")
    public ResponseEntity<Ordem> cancelOrdemUser(Long userId, @PathVariable("ordemId") Long ordemId) {

        return ResponseEntity.ok().body(ordemService.cancelOrdem(ordemId, userId));

    }

    @Operation(summary  = "Finaliza a ordem de serviço após o pedido ser entregue e atualiza o status para FINALIZADO", description  = "Envie para esse endpoint o id do usuário que ta finalizando a ordem e o id da ordem")
    @PutMapping("/ordens/{ordemId}/finalizar-pedido")
    public ResponseEntity<Ordem> receber(Long userId, @PathVariable("ordemId") Long ordemId) {

        return ResponseEntity.ok().body(ordemService.finalizarPedidos(ordemId, userId));

    }

    @Operation(summary  = "Realiza pagamento da ordem de serviço e atualiza o status para PAGO", description  = "Envie para esse endpoint o id do usuário que pagou a ordem de serviço e o id da ordem de serviço.")
    @PutMapping("/ordens/{ordemId}/confirmar-pagamento")
    public ResponseEntity<Ordem> confirmacao(Long userId, @PathVariable("ordemId") Long ordemId) {

        return ResponseEntity.ok().body(ordemService.confirmarPagamento(ordemId, userId));

    }

    @Operation(summary  = "Após o pagamento, atualiza o status da ordem de serviço para EMBALAGEM", description  = "Envie para esse endpoint o id do usuário que ta enviando o pedido para embalar e o id da ordem de serviço.")
    @PutMapping("/ordens/{ordemId}/embalar")
    public ResponseEntity<Ordem> embalar(Long userId, @PathVariable("ordemId") Long ordemId) {

        return ResponseEntity.ok().body(ordemService.embalar(ordemId, userId));

    }

    @Operation(summary  = "Após embalar e despachar para a entrega, atualiza o status da ordem de serviço para ENTREGA", description  = "Envie para esse endpoint o id do usuário que ta enviando o pedido e o id da ordem")
    @PutMapping("/ordens/{ordemId}/enviar")
    public ResponseEntity<Ordem> enviar(Long userId, @PathVariable("ordemId") Long ordemId) {

        return ResponseEntity.ok().body(ordemService.enviar(ordemId, userId));

    }

    @Operation(summary  = "Retorna todas as ordens existentes no banco de dados.")
    @GetMapping("/ordens")
    public ResponseEntity<List<Ordem>> findAllOrdemUser(Long userId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "25", required = false) int limit) {

        return ResponseEntity.ok().body(ordemService.findAllOrdemUser(userId, page, limit));

    }

    @GetMapping("/ordens/admin")
    public ResponseEntity<List<Ordem>> search(Long userId,
            @RequestParam(name = "filterText", defaultValue = "", required = false) String filterText,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "25", required = false) int limit) {

        return ResponseEntity.ok().body(ordemService.search(filterText, page, limit));

    }

    @Operation(summary  = "Adiciona uma nova categoria", description  = "Envie para esse endpoint o corpo do objeto")
    @PostMapping("/categoria")
    public Categoria create(@RequestBody CategoriaDto categoriaDto){
        return categoriaService.create(categoriaDto);
    }
    
    @Operation(summary  = "Atualiza uma categoria pelo id", description  = "Envie para esse endpoint o corpo do objeto contendo o id")
    @PutMapping("/categoria")
    public Categoria edit(@RequestBody Categoria categoria){
        return categoriaService.edit(categoria);
    }

    
    @Operation(summary  = "Deleta uma categoria pelo id", description  = "Envie para esse endpoint o id da categoria que deseja deletar")
    @DeleteMapping("/categoria/{id}")
    public void deleteCategoriaById(@PathVariable("id") Long id){
        categoriaService.deleteById(id);
    }

    @Operation(summary  = "Crie uma nova cor", description  = "Envie para esse endpoint o corpo da cor, contendo o nome e a categoria que ela pertence.")
    @PostMapping("/adicionar-cor")
    public ResponseEntity<Cor> postCor(@RequestBody CorDto corDto) {

        return ResponseEntity.ok().body(corService.postCor(corDto));

    }

    // @PostMapping("/user")
    // public ResponseEntity<?> addUser(@RequestBody UserDto userDto){
    //     UserDto createdUserDto = adminService.postUser(userDto);
    //     if(createdUserDto == null){
    //         return new ResponseEntity<>("Something went wrong.",HttpStatus.BAD_REQUEST);
    //     }else{
    //         return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    //     }
    // } 

    // @PostMapping("/user/google")
    // public ResponseEntity<?> addUserGoogle(@RequestBody UserGoogleProviderDto userGoogleProviderDto){
    //     UserDto createdUserDto = adminService.postUserGoogle(userGoogleProviderDto);
    //     if(createdUserDto == null){
    //         return new ResponseEntity<>("Something went wrong.",HttpStatus.BAD_REQUEST);
    //     }else{
    //         return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    //     }
    // } 

    // @GetMapping("/users")
    // public ResponseEntity<List<UserDto>> getAllUsers(){
        
    //     List<UserDto> allUsers = adminService.getAllUsers();

    //     return ResponseEntity.ok(allUsers);
    // }

    // @DeleteMapping("/user/{userId}")
    // public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
    //     adminService.deleteUser(userId);
    //     return ResponseEntity.noContent().build();
    // }

    // @GetMapping("/user/{userId}")
    // public ResponseEntity<SingleUserDto> getUserById(@PathVariable Long userId){
        
    //     SingleUserDto singleUserDto = adminService.getUserById(userId);

    //     if(singleUserDto==null)
    //         return ResponseEntity.notFound().build();
    //     return ResponseEntity.ok(singleUserDto);

    // }

    // @PutMapping("/user/{userId}")
    // public ResponseEntity<?> updateUser(@PathVariable Long userId,@RequestBody UserDto userDto){
    //     UserDto createdUserDto = adminService.updateUser(userId,userDto);
    //     if(createdUserDto ==null) return new ResponseEntity<>("Something went wrong.", HttpStatus.BAD_REQUEST);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    // }

}
