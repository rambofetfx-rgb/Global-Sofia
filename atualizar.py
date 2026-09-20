import json
import os

# Exemplo de dados que o robô pode atualizar automaticamente na nuvem
# Aqui você pode integrar uma API estável ou uma lógica de busca
catalogo = {
    "titulo": "GlobalAnimes Atualizado Automaticamente",
    "animes": [
        {
            "id": "naruto",
            "nome": "Naruto",
            "episodios": [
                {
                    "numero": 1,
                    "titulo": "Episódio 1 - Atualizado via Robô do GitHub",
                    "url": "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8"
                }
            ]
        }
    ]
}

# Caminho onde o arquivo JSON final será salvo para o app ler
caminho_json = "app/src/main/assets/animes.json"

os.makedirs(os.path.dirname(caminho_json), exist_ok=True)

with open(caminho_json, "w", encoding="utf-8") as f:
    json.dump(catalogo, f, ensure_ascii=False, indent=4)

print("Catálogo atualizado com sucesso pelo robô!")
