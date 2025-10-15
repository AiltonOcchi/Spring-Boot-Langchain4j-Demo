package br.com.occhi.suporte.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.occhi.suporte.services.AssistenteSuporteVendas;
import dev.langchain4j.service.Result;

@RestController
public class AssistenteSuporteVendasController {
	private final AssistenteSuporteVendas assistentesuporteVendas;
	
	public AssistenteSuporteVendasController( AssistenteSuporteVendas assistentesuporteVendas ) {
		this.assistentesuporteVendas = assistentesuporteVendas;
	}

	@GetMapping("/chat")
	public String chat(@RequestParam String sessionId, @RequestParam String message) {
		Result<String> result = assistentesuporteVendas.answer(sessionId, message);
		return result.content();
	}
}