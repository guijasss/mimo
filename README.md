# Tarefas

## 17/10/2024
* Tela de criação de anúncios de doação, através de um botão flutuante;
* Ler e escrever dados de doações no back-end, usando Firebase;
* Splash screen + animação;
* Incluir imagens do anúncio na home screen;
* Tela de login;

# 07/11/2024
* imagens do anúncio
  * upload a partir do device
* integração total com Firebase
  * armazenar imagens no storage
  * armazenar credenciais ok
  * recuperar credenciais OK
* tela de contato com o anunciante
* chat entre anunciante e interessado

# 14/11/2024
* atualizar lista de donations quando uma nova for criada
* persistir o login quando o app for fechado
* criar botão de Sair
* contatar usuário OK
    * retornar dados do usuário na requisição de Donation OK
    * usuário logado OK 
    * usuário que fez a doação OK
* listar chats
* filtrar apenas as doações que não foram feitas pelo usuário logado
* no backend enriqueci as informações de user no chat, consumir isso na listagem
  * atualizar a lastMessage quando a mensagem for enviadas
  * adicionar informações do donation no header da tela de mensagens
* mudar a lógica de nome mostrado no ChatList
  * se current_user = sender, mostra o recipient, e vice versa