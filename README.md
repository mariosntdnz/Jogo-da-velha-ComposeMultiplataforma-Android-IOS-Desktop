# Jogo da Velha Multiplataforma (KMP & CMP)

Este projeto é um **Jogo da Velha** desenvolvido com foco em **boas práticas de desenvolvimento**, seguindo princípios de **Clean Code**, **Clean Architecture** e **MVVM**, utilizando recursos modernos do Kotlin como **Flow**, **Coroutines**, **Navigation**, além de **Koin** para injeção de dependência, **Ktor** para requisições e lado do servidor e **Room** para persistência de dados.  

O projeto é **Kotlin Multiplatform (KMP)** e **Compose Multiplataforma (CMP)**, permitindo uma base de código compartilhada entre diferentes plataformas(Android, IOS e Desktop).

---

## Funcionalidades

- 🎮 **Novo jogo**: Comece uma partida do zero.  
- ⏯️ **Continuar jogo existente**: Retome partidas salvas.  
- 🔢 **Escolha do tamanho do grid**: De 3x3 até 10x10.  
- 📜 **Histórico de jogos**: Consulte partidas anteriores e acompanhe resultados.  
- 🌐 **Modo online via WebSocket**: Jogue em tempo real com outro dispositivo.  
  - O **WebSocket** foi implementado em **Kotlin KMP** em outro projeto. Confira em: [kotlin server](https://github.com/mariosntdnz/tic-tac-toe-server)

---

## Tecnologias e conceitos aplicados

- **Clean Code & Clean Architecture**  
- **MVVM**  
- **Kotlin Flow e Coroutines**  
- **Navigation Component**  
- **Koin** (injeção de dependência)  
- **Room** (persistência local)  
- **Kotlin Multiplatform (KMP) e CMP**  
- **WebSocket com ktor para comunicação em tempo real**

---

## Próximos passos

O projeto tem foco em **estudos e boas práticas**, mas há espaço para evolução, como a criação de salas para jogar. Por enquanto há apenas 2 jogadores pois o objetivo era aprender a criar o lado do servidor da comunicação com websocket.


