'use strict';

const authPage = document.querySelector('#auth-page');
const chatPage = document.querySelector('#chat-page');
const authForm = document.querySelector('#authForm');
const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const messageArea = document.querySelector('#messageArea');
const errorMessage = document.querySelector('#error-message');

let stompClient = null;
let currentUsername = null;

function registerAndConnect(event) {
    event.preventDefault();
    const username = document.querySelector('#username').value.trim();
    const password = document.querySelector('#password').value.trim();

    fetch('/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ userName: username, password: password })
    })
        .then(response => {
            if (response.ok) {
                currentUsername = username;
                connectWebSocket();
            } else {
                errorMessage.textContent = "Erro ao cadastrar. Tente novamente.";
                errorMessage.classList.remove('hidden');
            }
        })
        .catch(error => console.error('Erro na requisição:', error));
}

function connectWebSocket() {
    authPage.classList.add('hidden');
    chatPage.classList.remove('hidden');

    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    stompClient.subscribe('/topic/public', onMessageReceived);
}

function onError(error) {
    alert('Erro de conexão com o WebSocket. O servidor está rodando?');
}
function sendMessage(event) {
    event.preventDefault();
    const messageContent = messageInput.value.trim();

    if (messageContent && stompClient) {
        const chatMessage = {
            sender: currentUsername,
            content: messageContent,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
}

function onMessageReceived(payload) {
    const message = JSON.parse(payload.body);
    const messageElement = document.createElement('li');
    messageElement.textContent = `${message.sender}: ${message.content}`;
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

authForm.addEventListener('submit', registerAndConnect);
messageForm.addEventListener('submit', sendMessage);