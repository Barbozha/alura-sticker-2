package application;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

import util.GeradoraDeFigurinhas;
public class App {

	public static void main(String[] args) throws Exception {

		// FAZ A CONEXÃO COM O SERVIDOR IMDB
		String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
		URI endereco = URI.create(url);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		String body = response.body();
		// System.out.println(body);

		// EXTRAI OS DADOS QUE INTERESSAM (NOME DO FILME, CLASSIFICAÇÃO, DADOS DA IMAGEM
		// DO FILME)
		JsonParser parser = new JsonParser();
		List<Map<String, String>> listaDeFilmes = parser.parse(body);

		// EXIBE OS DADOS ORIUNDO DA API DO IMDB
		GeradoraDeFigurinhas geradora = new GeradoraDeFigurinhas();
		for (Map<String, String> filme : listaDeFilmes) {

			String urlImagem = filme.get("image");
			String titulo = filme.get("title");

			InputStream inputStream = new URL(urlImagem).openStream();
			String nomeArquivo = titulo + ".png";
			double classificacao = Double.parseDouble(filme.get("imDbRating"));
			geradora.criar(inputStream, nomeArquivo, classificacao);

			System.out.println(titulo);
			System.out.println();

		}

	}

}
