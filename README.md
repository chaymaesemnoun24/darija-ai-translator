# Traducteur AI Anglais ⇄ Darija

### Description du Projet

Application complète développée dans le cadre d'un Projet .
Ce système permet la traduction intelligente de textes anglais vers la Darija marocaine authentique en utilisant la puissance de l'IA générative (Google Gemini).

Ce projet met en œuvre une architecture hybride :
- Application Web : Une interface autonome pour la traduction de textes.
- Extension Navigateur : Un module intégré pour traduire le contenu web à la volée.

### 📺 Démonstration Vidéo

Voici une vidéo de démonstration (Haute Qualité) sur Google Drive :
[ lien ](https://drive.google.com/file/d/1cHxvd0o_HXogxc4yuf0zxknkCHEE8m-G/view?usp=sharing)

### ✨ Fonctionnalités

- Traduction contextuelle (comprend les idiomes et le langage courant).
- Double interface utilisateur (Web App & Extension Chrome).
- Intégration directe de l'API Google Gemini.
- Interface graphique moderne et responsive.
- Gestion des utilisateurs (Inscription & Connexion sécurisée).

### 🛠️ Technologies

- Java / Jakarta EE (Backend)
- PHP / HTML5 / CSS3 (Frontend)
- JavaScript / Manifest V3 (Extension)
- Google Gemini API (Intelligence Artificielle)
- IntelliJ IDEA / Maven

### 🔐 Architecture de Sécurité

La sécurité de l'application est intégrée directement dans la couche Backend (Backend-level security).
L'authentification est gérée implicitement via des **Filtres** (Middleware), garantissant que chaque requête vers l'API Gemini est vérifiée sans surcharger le contrôleur principal.
