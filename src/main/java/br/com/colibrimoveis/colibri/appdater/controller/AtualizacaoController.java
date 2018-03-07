package br.com.colibrimoveis.colibri.appdater.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.colibrimoveis.colibri.appdater.DAO.AtualizacaoDAO;
import br.com.colibrimoveis.colibri.appdater.DAO.FotosDoProdutoDAO;
import br.com.colibrimoveis.colibri.appdater.DAO.ProdutoDAO;
import br.com.colibrimoveis.colibri.appdater.DAO.ProdutoEAtualizacaoDAO;
import br.com.colibrimoveis.colibri.appdater.model.Atualizacao;
import br.com.colibrimoveis.colibri.appdater.model.Produto;
import br.com.colibrimoveis.colibri.appdater.model.ProdutoEAtualizacao;
import br.com.colibrimoveis.colibri.appdater.utils.AmbienteJson;
import br.com.colibrimoveis.colibri.appdater.utils.AtualizacaoJson;
import br.com.colibrimoveis.colibri.appdater.utils.CaracteristicaJson;
import br.com.colibrimoveis.colibri.appdater.utils.CategoriaJson;
import br.com.colibrimoveis.colibri.appdater.utils.CorJson;
import br.com.colibrimoveis.colibri.appdater.utils.FotoJson;
import br.com.colibrimoveis.colibri.appdater.utils.GeraPacoteApp;
import br.com.colibrimoveis.colibri.appdater.utils.ProdutoJson;
import br.com.colibrimoveis.colibri.appdater.utils.ProdutoParaAtualizacaoTemp;

@Controller
@RequestMapping(path="/atualizacao")
public class AtualizacaoController {
	
	@Autowired
	private ProdutoDAO produtoDAO;
	@Autowired
	private AtualizacaoDAO atualizacaoDAO;
	@Autowired
	private ProdutoEAtualizacaoDAO produtoEAtualizacaoDAO;
	@Autowired
	private FotosDoProdutoDAO fotosDoProdutoDAO;
	@Autowired
	private ProdutoParaAtualizacaoTemp prodTemp = new ProdutoParaAtualizacaoTemp();
	private ProdutoEAtualizacao produtoEAtualizacao = new ProdutoEAtualizacao();
	private List<ProdutoParaAtualizacaoTemp> prodTempList = new ArrayList<>();
	private List<Atualizacao> atualizacoes = new ArrayList<>();
	@Autowired
	private HttpServletRequest request;
	
	private int count = 0;
	
	@RequestMapping
	public ModelAndView init(Atualizacao atualizacao){
		ModelAndView mv = new ModelAndView("atualizacao/adicionar");
		prodTempList.clear();
		count = 0;
		for (Produto produto : getTodosProdutos()) {
			prodTemp.setProduto(produto);
			prodTemp.setAtualizacao(false);
			prodTempList.add(prodTemp);
			produto = new Produto();
			prodTemp = new ProdutoParaAtualizacaoTemp();
		}
		mv.addObject("todosProdutos", prodTempList);
		return mv;
	}
	
	@RequestMapping(value="/{id}/incluir", method=RequestMethod.PUT)
	public @ResponseBody void inserirNaAtualizacao(@PathVariable Long id){
		prodTempList.forEach(prod -> {
			if(prod.getProduto().getId() == id){
				if(prod.isAtualizacao()){
					prod.setAtualizacao(false);
					count--;
				}else{
					prod.setAtualizacao(true);
					count++;
				}
			}
		});
	}
	
	@RequestMapping(path="/salvar", method=RequestMethod.POST)
	public ModelAndView criarAtualizacao(@Validated Atualizacao atualizacao, Errors errors, 
			RedirectAttributes attr){
		if(errors.hasErrors() || count < 1){
			ModelAndView mv = new ModelAndView("atualizacao/adicionar");
			mv.addObject("todosProdutos", prodTempList);
			if(count < 1){
				mv.addObject("produtoMinimo", "É necessário ao menos um produto para criar uma atualização!");
			}
			return mv;
		}
		atualizacao.setData(new Date());
		Atualizacao atualizacaoTemp = atualizacaoDAO.save(atualizacao);
		
		prodTempList.forEach(produto-> {
			if(produto.isAtualizacao()){
				produtoEAtualizacao.setAtualizacao(atualizacaoTemp);
				produtoEAtualizacao.setProduto(produto.getProduto());
				produtoEAtualizacaoDAO.save(produtoEAtualizacao);
				produtoEAtualizacao = new ProdutoEAtualizacao();
			}
		});
		ModelAndView mv = new ModelAndView("redirect:/atualizacao");
		return mv;
	}
	
	//métodos de geração do arquivo json
	//cria lista com todas as atualizações já criadas
	@RequestMapping(path="/setup", method=RequestMethod.GET)
	public ModelAndView init2(){
		ModelAndView mv = new ModelAndView("atualizacao/setup");
		atualizacoes = atualizacaoDAO.findAll();
		mv.addObject("atualizacoes", atualizacoes);
		return mv;
	}
	
	//ARRUMAR ESSA POG
	@RequestMapping(value="/{id}/gerarJson", method=RequestMethod.PUT)
	public @ResponseBody void gerarJson(@PathVariable Long id){
		List<ProdutoJson> produtosJson = new ArrayList<>();
		Atualizacao one = atualizacaoDAO.getOne(id);
		one.getProdutos().forEach(p->{
			List<FotoJson> fotosJson = new ArrayList<>();
			List<CorJson> coresJson = new ArrayList<>();
			List<CaracteristicaJson> caracteristicasJson = new ArrayList<>();
			List<CategoriaJson> categoriasJson = new ArrayList<>();
			List<AmbienteJson> ambientesJson = new ArrayList<>();
			ProdutoJson produtoJson = new ProdutoJson();
			produtoJson.setId(p.getProduto().getId().toString());
			produtoJson.setCodigo(p.getProduto().getCodigo().toString());
			produtoJson.setLargura(p.getProduto().getLargura().toString());
			produtoJson.setAltura(p.getProduto().getAltura().toString());
			produtoJson.setProfundidade(p.getProduto().getProfundidade().toString());
			produtoJson.setVolumes(p.getProduto().getVolumes().toString());
			produtoJson.setPeso(p.getProduto().getPeso().toString());
			produtoJson.setNome(p.getProduto().getNome().toString());
			produtoJson.setFoto(p.getProduto().getFotoPrincipal().toString());
			fotosDoProdutoDAO.findByProduto(p.getProduto()).forEach(f -> {
				FotoJson foto = new FotoJson();
				foto.setDiretorio(f.getDiretorio());
				fotosJson.add(foto);
			});
			produtoJson.setFotos(fotosJson);
			p.getProduto().getCores().forEach(cor -> {
				CorJson corJson = new CorJson();
				corJson.setDescricao(cor.getNome());
				coresJson.add(corJson);
			});
			produtoJson.setCores(coresJson);
			p.getProduto().getCaracteristicas().forEach(car -> {
				CaracteristicaJson caracteristicaJson = new CaracteristicaJson();
				caracteristicaJson.setDescricao(car.getNome().toString());
				caracteristicasJson.add(caracteristicaJson);
			});
			produtoJson.setCaracteristicas(caracteristicasJson);
			p.getProduto().getCategorias().forEach(categ -> {
				CategoriaJson categoriaJson = new CategoriaJson();
				AmbienteJson ambienteJson = new AmbienteJson(); 
				ambienteJson.setDescricao(categ.getAmbiente().getNome().toString());
				ambientesJson.add(ambienteJson);
				categoriaJson.setDescricao(categ.getNome().toString());
				categoriasJson.add(categoriaJson);
			});
			produtoJson.setCategorias(categoriasJson);
			produtoJson.setAmbientes(ambientesJson);
			produtosJson.add(produtoJson);
		});
		AtualizacaoJson atualizacaoJson = new AtualizacaoJson();
		atualizacaoJson.setProdutosJson(produtosJson);
		System.out.println(one.getNome());
		System.out.println(atualizacaoJson.getProdutosJson().size());
		GeraPacoteApp gerador = new GeraPacoteApp();
		gerador.play(atualizacaoJson, one.getNome(), request.getServletContext().getRealPath("//"));
		
		/*ObjectMapper mapper = new ObjectMapper();
		File diretorioJs = new File("C:\\appdater\\" + one.getId() + one.getNome() + "\\json");
		File diretorioImgs = new File("C:\\appdater\\" + one.getId() + one.getNome() + "\\img");
		String dirPadrao = "C:\\appdater\\" + one.getId() + one.getNome();
		diretorioJs.mkdirs();
		diretorioImgs.mkdir();
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(diretorioJs.getPath() + "/produtos.js"), atualizacaoJson);
			one.getProdutos().forEach(p -> {
				p.getProduto().getFotos().forEach(foto -> {
					String dir = request.getServletContext().getRealPath("/" + foto.getDiretorio().toString());
					Path origem = Paths.get(dir);
					Path destino = Paths.get(dirPadrao + "\\" + foto.getDiretorio().toString());
					File file = new File(destino.toString());
					try {
						if(!file.exists())
							Files.copy(origem, destino);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			});
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
	@RequestMapping(path="/excluir/{id}", method=RequestMethod.POST)
	public ModelAndView excluir(@PathVariable Long id){
		Produto produto = new Produto();
		produto = produtoDAO.findOne(id);
		produto.setSituacao(false);
		produtoDAO.save(produto);
		ModelAndView mv = new ModelAndView("redirect:/atualizacao");
		return mv;
	}
	
	//métodos internos
	private List<Produto> getTodosProdutos(){
		return produtoDAO.findBySituacao();
	}
}
