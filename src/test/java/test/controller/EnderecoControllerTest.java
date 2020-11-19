package test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goku.goku.ecommerce.controller.CadastroEnderecoController;
import com.goku.goku.ecommerce.controller.advice.EnderecoAdvice;
import com.goku.goku.ecommerce.exception.EnderecoDuplicadoException;
import com.goku.goku.ecommerce.exception.EnderecoNotFoundException;
import com.goku.goku.ecommerce.model.builder.EnderecoBuilder;
import com.goku.goku.ecommerce.model.builder.EnderecoCEPDetalheBuilder;
import com.goku.goku.ecommerce.model.endereco.Endereco;
import com.goku.goku.ecommerce.model.endereco.EnderecoCEPDetalhe;
import com.goku.goku.ecommerce.model.presenter.EnderecoCEPDetalheResponse;
import com.goku.goku.ecommerce.service.EnderecoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
public class EnderecoControllerTest {

	private static final String PATH_APP = "/api/v1/enderecos";
	private static final String POST_CREATE_ENDERECO = PATH_APP;
	private static final String GET_LIST_ALL_ENDERECO = PATH_APP;
	private static final String PUT_EDIT_ENDERECO = PATH_APP.concat("/{cep}");
	private static final String GET_ENDERECO_BY_CEP = PATH_APP.concat("/{cep}");
	private static final String DELETE_ENDERECO = PATH_APP.concat("/{cep}");

	@InjectMocks
	private CadastroEnderecoController enderecoController;

	@Mock
	private EnderecoService enderecoService;

	private MockMvc mockMVC;

	private ObjectMapper mapper;

	@BeforeEach
	public void setup() {

		this.mockMVC = MockMvcBuilders.standaloneSetup(enderecoController)
				.setControllerAdvice(new EnderecoAdvice()).build();
		this.mapper = new ObjectMapper();

	}

	@Test
	void deveCriarEndereco() throws JsonProcessingException, Exception {

		Endereco novoEndereco = new EnderecoBuilder().cep(25666357l).logradouro("Rua teste").numero(666l)
				.bairro("Bairro teste").cidade("Cidade teste").estado("Teste do norte").pais("Teste").build();
		MvcResult response = mockMVC.perform(post(POST_CREATE_ENDERECO).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(novoEndereco))).andDo(print()).andReturn();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.NO_CONTENT);

	}

	@Test
	void naoDeveCriarEnderecoPoisOCampoCEPNaoFoiFornecido() throws Exception {

		Endereco novoEndereco = new EnderecoBuilder().logradouro("Rua teste").numero(666l)
				.bairro("Bairro teste").cidade("Cidade teste").estado("Teste do norte").pais("Teste").build();
		MvcResult response = mockMVC.perform(post(POST_CREATE_ENDERECO).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(novoEndereco))).andDo(print()).andReturn();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.BAD_REQUEST);

	}

	@Test
	void naoDeveCriarEnderecoPoisOCampoCEPFoiFornecidoComValorAbaixoDoMinimo() throws Exception {

		Endereco novoEndereco = new EnderecoBuilder().cep(1l).logradouro("Rua teste").numero(666l)
				.bairro("Bairro teste").cidade("Cidade teste").estado("Teste do norte").pais("Teste").build();
		MvcResult response = mockMVC.perform(post(POST_CREATE_ENDERECO).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(novoEndereco))).andDo(print()).andReturn();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.BAD_REQUEST);

	}

	@Test
	void naoDeveCriarEnderecoPoisOCampoCEPFoiFornecidoComValorAcimaDoMaximo() throws Exception {

		Endereco novoEndereco = new EnderecoBuilder().cep(99999999999999l).logradouro("Rua teste")
				.numero(666l).bairro("Bairro teste").cidade("Cidade teste").estado("Teste do norte").pais("Teste")
				.build();
		MvcResult response = mockMVC.perform(post(POST_CREATE_ENDERECO).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(novoEndereco))).andDo(print()).andReturn();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.BAD_REQUEST);

	}

	@Test
	void naoDeveCriarEnderecoPoisOCampoLogradouroNaoFoiFornecido() throws Exception {

		Endereco novoEndereco = new EnderecoBuilder().cep(65888254l).numero(666l).bairro("Bairro teste")
				.cidade("Cidade teste").estado("Teste do norte").pais("Teste").build();
		MvcResult response = mockMVC.perform(post(POST_CREATE_ENDERECO).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(novoEndereco))).andDo(print()).andReturn();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.BAD_REQUEST);

	}

	@Test
	void naoDeveCriarEnderecoPoisOCampoNumeroNaoFoiFornecido() throws Exception {

		Endereco novoEndereco = new EnderecoBuilder().cep(65888254l).logradouro("Rua teste")
				.bairro("Bairro teste").cidade("Cidade teste").estado("Teste do norte").pais("Teste").build();
		MvcResult response = mockMVC.perform(post(POST_CREATE_ENDERECO).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(novoEndereco))).andDo(print()).andReturn();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.BAD_REQUEST);

	}

	@Test
	void naoDeveCriarEnderecoPoisOCampoNumeroFoiFornecidoComValorAbaixoDoMinimo() throws Exception {

		Endereco novoEndereco = new EnderecoBuilder().cep(65888254l).logradouro("Rua teste").numero(0l)
				.bairro("Bairro teste").cidade("Cidade teste").estado("Teste do norte").pais("Teste").build();
		MvcResult response = mockMVC.perform(post(POST_CREATE_ENDERECO).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(novoEndereco))).andDo(print()).andReturn();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.BAD_REQUEST);

	}

	@Test
	void naoDeveCriarEnderecoPoisOCampoNumeroFoiFornecidoComValorCimaDoMaximo() throws Exception {

		Endereco novoEndereco = new EnderecoBuilder().cep(65888254l).logradouro("Rua teste")
				.numero(9999999999999999l).bairro("Bairro teste").cidade("Cidade teste").estado("Teste do norte")
				.pais("Teste").build();
		MvcResult response = mockMVC.perform(post(POST_CREATE_ENDERECO).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(novoEndereco))).andDo(print()).andReturn();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.BAD_REQUEST);

	}

	@Test
	void naoDeveCriarEnderecoPoisOCampoBairroNaoFoiFornecido() throws Exception {

		Endereco novoEndereco = new EnderecoBuilder().cep(65888254l).logradouro("Rua teste").numero(666l)
				.cidade("Cidade teste").estado("Teste do norte").pais("Teste").build();
		MvcResult response = mockMVC.perform(post(POST_CREATE_ENDERECO).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(novoEndereco))).andDo(print()).andReturn();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.BAD_REQUEST);

	}

	@Test
	void naoDeveCriarEnderecoPoisOCampoCidadeNaoFoiFornecido() throws Exception {

		Endereco novoEndereco = new EnderecoBuilder().cep(65888254l).logradouro("Rua teste").numero(666l)
				.bairro("Bairro teste").estado("Teste do norte").pais("Teste").build();
		MvcResult response = mockMVC.perform(post(POST_CREATE_ENDERECO).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(novoEndereco))).andDo(print()).andReturn();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.BAD_REQUEST);

	}

	@Test
	void naoDeveCriarEnderecoPoisOCampoEstadoNaoFoiFornecido() throws Exception {

		Endereco novoEndereco = new EnderecoBuilder().cep(65888254l).logradouro("Rua teste").numero(666l)
				.bairro("Bairro teste").cidade("Cidade teste").pais("Teste").build();
		MvcResult response = mockMVC.perform(post(POST_CREATE_ENDERECO).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(novoEndereco))).andDo(print()).andReturn();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.BAD_REQUEST);

	}

	@Test
	void naoDeveCriarEnderecoPoisOCampoPaisNaoFoiFornecido() throws Exception {

		Endereco novoEndereco = new EnderecoBuilder().cep(65888254l).logradouro("Rua teste").numero(666l)
				.bairro("Bairro teste").cidade("Cidade teste").estado("Teste do norte").build();
		MvcResult response = mockMVC.perform(post(POST_CREATE_ENDERECO).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(novoEndereco))).andDo(print()).andReturn();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.BAD_REQUEST);

	}

	@Test
	void naoDeveCriarEnderecoPoisJaExiste() throws JsonProcessingException, Exception {

		doThrow(EnderecoDuplicadoException.class).when(enderecoService).criarEndereco(any(Endereco.class));

		Endereco novoEndereco = new EnderecoBuilder().cep(25666357l).logradouro("Rua teste").numero(666l)
				.bairro("Bairro teste").cidade("Cidade teste").estado("Teste do norte").pais("Teste").build();
		MvcResult response = mockMVC.perform(post(POST_CREATE_ENDERECO).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(novoEndereco))).andDo(print()).andReturn();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.CONFLICT);

	}

	@Test
	void deveListarTodosEnderecos() throws Exception {

		when(enderecoService.listarEnderecos()).thenReturn(new EnderecoBuilder().quantidadeItens(10).buildList());

		MvcResult response = mockMVC.perform(get(GET_LIST_ALL_ENDERECO)).andDo(print()).andReturn();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.OK);
		assertThat(mapper.readValue(response.getResponse().getContentAsString(), Endereco.class).getEnderecos())
				.hasSize(10);

	}

	@Test
	void deveEditarEndereco() throws Exception {

		Endereco editarEnderecoDTO = new EnderecoBuilder().bairro("Bairro teste")
				.cidade("Cidade teste").build();

		Map<String, Object> variable = new HashMap<String, Object>();
		variable.put("cep", 12365478);
		URI editEndereco = UriComponentsBuilder.fromPath(PUT_EDIT_ENDERECO).buildAndExpand(variable).toUri();

		MvcResult response = mockMVC.perform(put(editEndereco).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(editarEnderecoDTO))).andDo(print()).andReturn();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.NO_CONTENT);
		verify(enderecoService, times(1)).editarEndereco(anyLong(), any(Endereco.class));

	}

	@Test
	void naoDeveEditarEnderecoPoisNaoFoiEncontrado() throws JsonProcessingException, Exception {

		doThrow(EnderecoNotFoundException.class).when(enderecoService).editarEndereco(anyLong(),
				any(Endereco.class));

		Endereco editarEnderecoDTO = new EnderecoBuilder().bairro("Bairro teste")
				.cidade("Cidade teste").build();

		Map<String, Object> variable = new HashMap<String, Object>();
		variable.put("cep", 12365478);
		URI getEndereco = UriComponentsBuilder.fromPath(PUT_EDIT_ENDERECO).buildAndExpand(variable).toUri();

		MvcResult response = mockMVC.perform(put(getEndereco).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(editarEnderecoDTO))).andDo(print()).andReturn();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.NOT_FOUND);

	}

	@Test
	void naoDeveEditarEnderecoPoisAsInformacoesNaoForamPreenchidos() throws JsonProcessingException, Exception {

		Endereco editarEnderecoDTO = new EnderecoBuilder().build();

		Map<String, Object> variable = new HashMap<String, Object>();
		variable.put("cep", 12365478);
		URI getEndereco = UriComponentsBuilder.fromPath(PUT_EDIT_ENDERECO).buildAndExpand(variable).toUri();

		MvcResult response = mockMVC.perform(put(getEndereco).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(editarEnderecoDTO))).andDo(print()).andReturn();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.BAD_REQUEST);

	}

	@Test
	void naoDeveEditarPoisOCampoNumeroFoiFornecidoComValorAcimaDoMaximo() throws JsonProcessingException, Exception {

		Endereco editarEnderecoDTO = new EnderecoBuilder().numero(999999999999999999l).build();

		Map<String, Object> variable = new HashMap<String, Object>();
		variable.put("cep", 25666365);
		URI editEndereco = UriComponentsBuilder.fromPath(PUT_EDIT_ENDERECO).buildAndExpand(variable).toUri();

		MvcResult response = mockMVC.perform(put(editEndereco).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(editarEnderecoDTO))).andDo(print()).andReturn();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.BAD_REQUEST);
		verify(enderecoService, never()).editarEndereco(anyLong(), any(Endereco.class));

	}

	@Test
	void naoDeveEditarPoisOCampoNumeroFoiFornecidoComValorAbaixoDoMinimo() throws JsonProcessingException, Exception {

		Endereco editarEnderecoDTO = new EnderecoBuilder().numero(0l).build();

		Map<String, Object> variable = new HashMap<String, Object>();
		variable.put("cep", 25666365);
		URI editEndereco = UriComponentsBuilder.fromPath(PUT_EDIT_ENDERECO).buildAndExpand(variable).toUri();

		MvcResult response = mockMVC.perform(put(editEndereco).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(editarEnderecoDTO))).andDo(print()).andReturn();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.BAD_REQUEST);
		verify(enderecoService, never()).editarEndereco(anyLong(), any(Endereco.class));

	}

	@Test
	void deveBuscarPorCEP() throws Exception {

		EnderecoCEPDetalhe enderecoCEPDetalheBuilt = new EnderecoCEPDetalheBuilder().cep(25666357l)
				.logradouro("Rua teste").numero(666l).bairro("Bairro teste").cidade("Cidade teste")
				.estado("Teste do norte").pais("Teste").build();
		when(enderecoService.buscarPorCEP(anyLong())).thenReturn(enderecoCEPDetalheBuilt);

		Map<String, Object> variable = new HashMap<String, Object>();
		variable.put("cep", 12365478);
		URI getEnderecoByCep = UriComponentsBuilder.fromPath(GET_ENDERECO_BY_CEP).buildAndExpand(variable).toUri();

		MvcResult response = mockMVC.perform(get(getEnderecoByCep)).andDo(print()).andReturn();
		EnderecoCEPDetalhe enderecoCEPDetalheDTO = mapper
				.readValue(response.getResponse().getContentAsString(), EnderecoCEPDetalheResponse.class)
				.getEnderecoCEPDetalhe();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.OK);
		assertThat(enderecoCEPDetalheDTO.getCep()).isEqualTo(enderecoCEPDetalheBuilt.getCep());
		assertEquals(enderecoCEPDetalheDTO.getLogradouro(), enderecoCEPDetalheBuilt.getLogradouro());
		assertEquals(enderecoCEPDetalheDTO.getNumero(), enderecoCEPDetalheBuilt.getNumero());
		assertEquals(enderecoCEPDetalheDTO.getBairro(), enderecoCEPDetalheBuilt.getBairro());
		assertEquals(enderecoCEPDetalheDTO.getCidade(), enderecoCEPDetalheBuilt.getCidade());
		assertEquals(enderecoCEPDetalheDTO.getEstado(), enderecoCEPDetalheBuilt.getEstado());
		assertEquals(enderecoCEPDetalheDTO.getPais(), enderecoCEPDetalheBuilt.getPais());

	}

	@Test
	public void naoDeveBuscarPorCEPPoisEnderecoNaoFoiEncontrado() throws Exception {

		doThrow(EnderecoNotFoundException.class).when(enderecoService).buscarPorCEP(anyLong());

		Map<String, Object> variable = new HashMap<String, Object>();
		variable.put("cep", 12365478);
		URI getEnderecoByCep = UriComponentsBuilder.fromPath(GET_ENDERECO_BY_CEP).buildAndExpand(variable).toUri();

		MvcResult response = mockMVC.perform(get(getEnderecoByCep)).andDo(print()).andReturn();

		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.NOT_FOUND);

	}

	@Test
	void deveDeletarEndereco() throws Exception {

		Map<String, Object> variable = new HashMap<String, Object>();
		variable.put("cep", 12365478);
		URI deleteByCep = UriComponentsBuilder.fromPath(DELETE_ENDERECO).buildAndExpand(variable).toUri();

		MvcResult response = mockMVC.perform(delete(deleteByCep)).andDo(print()).andReturn();

		verify(enderecoService, times(1)).deletarEndereco(anyLong());
		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.NO_CONTENT);

	}

	@Test
	void naoDeveDeletarEnderecoPoisEnderecoNaoFoiEncontrado() throws Exception {

		doThrow(EnderecoNotFoundException.class).when(enderecoService).deletarEndereco(anyLong());

		Map<String, Object> variable = new HashMap<String, Object>();
		variable.put("cep", 12365478);
		URI deleteByCep = UriComponentsBuilder.fromPath(DELETE_ENDERECO).buildAndExpand(variable).toUri();

		MvcResult response = mockMVC.perform(delete(deleteByCep)).andDo(print()).andReturn();
		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.NOT_FOUND);

	}

	@Test
	void deveRetornarInternalServerError() throws Exception {

		doThrow(RuntimeException.class).when(enderecoService).deletarEndereco(anyLong());

		Map<String, Object> variable = new HashMap<String, Object>();
		variable.put("cep", 12365478);
		URI deleteByCep = UriComponentsBuilder.fromPath(DELETE_ENDERECO).buildAndExpand(variable).toUri();

		MvcResult response = mockMVC.perform(delete(deleteByCep)).andDo(print()).andReturn();
		assertThat(HttpStatus.valueOf(response.getResponse().getStatus())).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
