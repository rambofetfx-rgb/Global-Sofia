import json
import os
from datetime import datetime
import urllib.request

def buscar_lancamentos():
    try:
        # Exemplo consumindo uma API pública de animes recentes/populares
        url_api = "https://api.jikan.moe/v4/seasons/now"
        req = urllib.request.Request(
            url_api, 
            headers={'User-Agent': 'Mozilla/5.0'}
        )
        
        with urllib.request.urlopen(req) as resposta:
            dados_raw = resposta.read().decode('utf-8')
            dados = json.loads(dados_raw)
            
            animes_lista = []
            
            # Pega os primeiros animes da temporada atual que a API retornar
            for item in dados.get('data', [])[:10]: # Pega os 10 principais
                nome_anime = item.get('title', 'Anime Desconhecido')
                anime_id = str(item.get('mal_id'))
                capa = item.get('images', {}).get('jpg', {}).get('image_url', '')
                
                # Como APIs de metadados às vezes não dão o link direto do player em vídeo HLS (.m3u8), 
                # o robô já deixa estruturado com um player padrão ou o link de teste para os episódios recentes
                anime_obj = {
                    "id": anime_id,
                    "nome": nome_anime,
                    "capa": capa,
                    "episodios": [
                        {
                            "numero": 1,
                            "titulo": f"Lançamento Recente - {nome_anime}",
                            # Aqui o robô pode puxar o link direto se integrado a uma API de player, 
                            # ou usar um player de stream padrão
                            "url": "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8"
                        }
                    ]
                }
                animes_lista.append(anime_obj)
                
            return animes_lista
            
    except Exception as e:
        print(f"Erro ao buscar na API: {e}")
        return []

# Executa a busca automática
animes_atualizados = buscar_lancamentos()

# Se a API falhar por algum bloqueio momentâneo, mantém uma estrutura base para não zerar o app
if not animes_atualizados:
    animes_atualizados = [
        {
            "id": "fallback",
            "nome": "Aguardando próxima sincronização...",
            "capa": "",
            "episodios": [{"numero": 1, "titulo": "Tentando reconectar à API...", "url": ""}]
        }
    ]

catalogo = {
    "titulo": "GlobalAnimes - Lançamentos Automáticos",
    "ultima_atualizacao": datetime.now().strftime("%d/%m/%Y %H:%M:%S"),
    "animes": animes_atualizados
}

# Salva o resultado na raiz para o GitHub Pages publicar instantaneamente
with open("animes.json", "w", encoding="utf-8") as f:
    json.dump(catalogo, f, ensure_ascii=False, indent=4)

print("Catálogo de lançamentos atualizado automaticamente com sucesso!")
