package test.service;

import com.goku.goku.ecommerce.exception.EnderecoDuplicadoException;
import com.goku.goku.ecommerce.exception.EnderecoNotFoundException;
import com.goku.goku.ecommerce.model.builder.EnderecoBuilder;
import com.goku.goku.ecommerce.model.endereco.Endereco;
import com.goku.goku.ecommerce.model.endereco.EnderecoCEPDetalhe;
import com.goku.goku.ecommerce.repository.EnderecoRepository;
import com.goku.goku.ecommerce.service.impl.EnderecoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class EnderecoServiceImplTest {

	@Mock
	private EnderecoRepository enderecoRepository;

	@InjectMocks
	private EnderecoServiceImpl enderecoService;

	@Test
	public void shouldCriarEndereco() {

		Endereco enderecoBuild = null;
		when(enderecoRepository.findByCep(anyLong())).thenReturn(Optional.ofNullable(enderecoBuild));

		Endereco novoEndereco = new EnderecoBuilder().cep(25666357l).logradouro("Rua teste").numero(666l)
				.bairro("Bairro teste").cidade("Cidade teste").estado("Teste do norte").pais("Teste").build();

		enderecoService.criarEndereco(novoEndereco);

		ArgumentCaptor<Endereco> argCaptor = ArgumentCaptor.forClass(Endereco.class);
		verify(enderecoRepository, times(1)).save(argCaptor.capture());

		assertEquals(argCaptor.getValue().getCep(), novoEndereco.getCep());
		assertEquals(argCaptor.getValue().getLogradouro(), novoEndereco.getLogradouro());
		assertEquals(argCaptor.getValue().getNumero(), novoEndereco.getNumero());
		assertEquals(argCaptor.getValue().getBairro(), novoEndereco.getBairro());
		assertEquals(argCaptor.getValue().getCidade(), novoEndereco.getCidade());
		assertEquals(argCaptor.getValue().getEstado(), novoEndereco.getEstado());
		assertEquals(argCaptor.getValue().getPais(), novoEndereco.getPais());

	}

	@Test
	public void naoDeveCriarEnderecoPoisJaExiste() {

		Endereco enderecoBuild = new EnderecoBuilder().cep(25666357l).logradouro("Rua teste").numero(666l)
				.bairro("Bairro teste").cidade("Cidade teste").estado("Teste do norte").pais("Teste").build();
		when(enderecoRepository.findByCep(anyLong())).thenReturn(Optional.ofNullable(enderecoBuild));

		assertThrows(EnderecoDuplicadoException.class, () -> {
			enderecoService.criarEndereco(new EnderecoBuilder().cep(254212l).build());
		});

		verify(enderecoRepository, never()).save(any(Endereco.class));

	}

	@Test
	public void shouldListarEnderecos() {

		when(enderecoRepository.findAll()).thenReturn(new EnderecoBuilder().quantidadeItens(50).buildList());

		List<Endereco> enderecos = enderecoService.listarEnderecos();

		assertThat(enderecos).hasSize(50);

	}

	@Test
	public void shouldEditarEndereco() {

		Endereco enderecoBuild = new EnderecoBuilder().cep(25666357l).logradouro("Rua teste").numero(666l)
				.bairro("Bairro teste").cidade("Cidade teste").estado("Teste do norte").pais("Teste").build();
		when(enderecoRepository.findByCep(anyLong())).thenReturn(Optional.ofNullable(enderecoBuild));

		Endereco editarEnderecoBuild = new EnderecoBuilder().bairro("bairro alterado")
				.cidade("cidade alterada").build();
		enderecoService.editarEndereco(25666352l, editarEnderecoBuild);

		ArgumentCaptor<Endereco> argCaptor = ArgumentCaptor.forClass(Endereco.class);
		verify(enderecoRepository, times(1)).save(argCaptor.capture());

		assertThat(argCaptor.getValue().getBairro()).isEqualTo(editarEnderecoBuild.getBairro());
		assertThat(argCaptor.getValue().getCidade()).isEqualTo(editarEnderecoBuild.getCidade());

	}

	@Test
	public void shouldNotEditEnderecoBecauseEnderecoWasNotFound() {

		Endereco enderecoBuild = null;
		when(enderecoRepository.findByCep(anyLong())).thenReturn(Optional.ofNullable(enderecoBuild));

		Endereco editarEnderecoBuild = new EnderecoBuilder().bairro("bairro alterado")
				.cidade("cidade alterada").build();

		assertThrows(EnderecoNotFoundException.class, () -> {
			enderecoService.editarEndereco(25666352l, editarEnderecoBuild);
		});

		verify(enderecoRepository, never()).save(any());
	}

	@Test
	public void shouldBuscarPorCEP() {

		Endereco enderecoBuild = new EnderecoBuilder().cep(25666357l).logradouro("Rua teste").numero(666l)
				.bairro("Bairro teste").cidade("Cidade teste").estado("Teste do norte").pais("Teste").build();
		when(enderecoRepository.findByCep(anyLong())).thenReturn(Optional.ofNullable(enderecoBuild));

		EnderecoCEPDetalhe enderecoCEPDetalheDTO = enderecoService.buscarPorCEP(25666951l);
		assertThat(enderecoCEPDetalheDTO.getCep()).isEqualTo(enderecoBuild.getCep());
		assertThat(enderecoCEPDetalheDTO.getLogradouro()).isEqualTo(enderecoBuild.getLogradouro());
		assertThat(enderecoCEPDetalheDTO.getNumero()).isEqualTo(enderecoBuild.getNumero());
		assertThat(enderecoCEPDetalheDTO.getBairro()).isEqualTo(enderecoBuild.getBairro());
		assertThat(enderecoCEPDetalheDTO.getCidade()).isEqualTo(enderecoBuild.getCidade());
		assertThat(enderecoCEPDetalheDTO.getEstado()).isEqualTo(enderecoBuild.getEstado());
		assertThat(enderecoCEPDetalheDTO.getPais()).isEqualTo(enderecoBuild.getPais());

	}

	@Test
	public void shouldNotFindByCepBecauseEnderecoWasNotFound() {

		Endereco enderecoBuild = null;
		when(enderecoRepository.findByCep(anyLong())).thenReturn(Optional.ofNullable(enderecoBuild));

		assertThrows(EnderecoNotFoundException.class, () -> {
			enderecoService.buscarPorCEP(25666951l);
		});

	}

	@Test
	public void shouldDeleteEndereco() {

		Endereco enderecoBuild = new EnderecoBuilder().id(10l).cep(25666357l).logradouro("Rua teste").numero(666l)
				.bairro("Bairro teste").cidade("Cidade teste").estado("Teste do norte").pais("Teste").build();
		when(enderecoRepository.findByCep(anyLong())).thenReturn(Optional.ofNullable(enderecoBuild));

		enderecoService.deletarEndereco(69555258l);

		ArgumentCaptor<Long> argCaptor = ArgumentCaptor.forClass(Long.class);
		verify(enderecoRepository, times(1)).deleteById(argCaptor.capture());

		assertEquals(argCaptor.getValue(), 10l);

	}

	@Test
	public void sholdNotDeleteEnderecoBecauseEnderecoWasNotFound() {

		Endereco enderecoBuild = null;
		when(enderecoRepository.findByCep(anyLong())).thenReturn(Optional.ofNullable(enderecoBuild));

		assertThrows(EnderecoNotFoundException.class, () -> {
			enderecoService.deletarEndereco(69555258l);
		});
		verify(enderecoRepository, never()).deleteById(anyLong());

	}

}
