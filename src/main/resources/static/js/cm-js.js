//requisição ajax para inserir produto em uma lista
$('.btn-insere').click(function(event) {
	console.log("inserido");
	event.preventDefault();
	
	var btnIncluir = $(event.currentTarget);
	var urlIncluir = btnIncluir.attr('href');
	console.log(urlIncluir);
	var response = $.ajax({
		url: urlIncluir,
		type: 'PUT'
	});
	
	response.done(function(e){
		var codigoTitulo = btnIncluir.attr('data-role');
		if($('[data-role=' + codigoTitulo + ']').hasClass("btn-success")){
			$('[data-role=' + codigoTitulo + ']').removeClass("btn-success");
			$('[data-role=' + codigoTitulo + ']').addClass("btn-default");
		}else{
			$('[data-role=' + codigoTitulo + ']').addClass("btn-success");
		}
	});
});

$('.btn-geraJson').click(function(event){
	event.preventDefault();
	var btnGerar = $(event.currentTarget);
	var urlGerar = btnGerar.attr('href');
	console.log(urlGerar);
	var codigoBtn = btnGerar.attr('data-role');
	$('[data-role=' + codigoBtn + ']').removeClass("btn-success");
	$('[data-role=' + codigoBtn + ']').addClass("disabled");
	var response = $.ajax({
		url: urlGerar,
		type: 'PUT'
	});
	
	response.done(function(e){
		$('[data-role=' + codigoBtn + ']').removeClass("disabled");
		$('[data-role=' + codigoBtn + ']').addClass("btn-success");
	});
});

$('#confirmacao-editar').on('show.bs.modal', function(event){
	var botao = $(event.relatedTarget);
	var codigo = botao.data('codigo');
	var destino = botao.data('destino');
	var modal = $(this);
	var form = modal.find('form');
	var action = form.attr('action');
	action = '/'+destino;
	if(!action.endsWith('/')){
		action += '/';
	}
	form.attr('action', action+codigo);
	console.log(action+codigo);
});

$('#confirma').on('show.bs.modal', function(event){
	var botao = $(event.relatedTarget);
	var codigo = botao.data('codigo');
	var destino = botao.data('destino');
	var modal = $(this);
	var form = modal.find('form');
	var action = form.attr('action');
	action = '/'+destino;
	if(!action.endsWith('/')){
		action += '/';
	}
	form.attr('action', action+codigo);
	console.log(action+codigo);
	console.log(document.getElementById('form-confirma'));
});

$( document ).ready(function() {
	$('#categorias').multiSelect({
		selectableHeader: "<div class='custom-header'>Opções</div>",
		selectionHeader: "<div class='custom-header'>Selecionados</div>"
	});
	$('#cores').multiSelect({
		selectableHeader: "<div class='custom-header'>Opções</div>",
		selectionHeader: "<div class='custom-header'>Selecionados</div>"
	});
	$('#caracteristicas').multiSelect({
		selectableHeader: "<div class='custom-header'>Opções</div>",
		selectionHeader: "<div class='custom-header'>Selecionados</div>"
	});
});


