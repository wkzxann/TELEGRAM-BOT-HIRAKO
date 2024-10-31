package com.telegram.org.logica;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.telegram.org.entity.GeneratedUserAU;
import com.telegram.org.entity.GeneratedUserBR;
import com.telegram.org.entity.GeneratedUserCA;
import com.telegram.org.entity.GeneratedUserES;
import com.telegram.org.entity.GeneratedUserMX;
import com.telegram.org.entity.GeneratedUserUS;
import com.telegram.org.utils.MyInlineKeyboardButton;

@SuppressWarnings("deprecation")
@Service
public class HirakoBotService extends TelegramLongPollingBot {

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage()) {
			Message message = update.getMessage();
			if (message.hasText()) {
				if (message.getChat().isUserChat()) {
					handleMessageFromUser(message);
				} else if (message.getChat().isGroupChat()) {
					handleMessageFromGroup(message);
				}
			} else if (message.getNewChatMembers() != null && message.getChat().isGroupChat()) {
				handleNewChatMembers(message);
			}
		} else if (update.hasCallbackQuery()) {
			String callbackData = update.getCallbackQuery().getData();
			handleCallbackQuery(update, callbackData);
		}
	}

	private void handleMessageFromUser(Message message) {
	    String mensajeRecibido = message.getText();
	    final long chatId = message.getChatId();

	    SendMessage mensajeAEnviar = new SendMessage();
	    mensajeAEnviar.setChatId(chatId);

	    String Username = message.getFrom().getUserName();
	    String Name = message.getFrom().getFirstName();

	    String mensajePersonalizado = "";

	    if ("/start".equalsIgnoreCase(mensajeRecibido)) {
	        mensajePersonalizado = "¡Hola " + (Username != null && !Username.isEmpty() ? Username : Name)
	                + "!, Bienvenido(a) a HirakoBot. ¿En qué puedo ayudarte hoy? Por favor, selecciona una opción a continuación:";

	        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
	        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

	        List<InlineKeyboardButton> buttonsRowOne = new ArrayList<>();
	        buttonsRowOne.add(new MyInlineKeyboardButton("Generar Usuario 👤", "comandos"));
	        buttonsRowOne.add(new MyInlineKeyboardButton("Ayuda ❓", "ayuda"));

	        rowsInline.add(buttonsRowOne);

	        markupInline.setKeyboard(rowsInline);
	        mensajeAEnviar.setReplyMarkup(markupInline);
	    } else {
	        return;
	    }

	    mensajeAEnviar.setText(mensajePersonalizado);

	    try {
	        execute(mensajeAEnviar);
	    } catch (TelegramApiException e) {
	        e.printStackTrace();
	    }
	}

	private void handleMessageFromGroup(Message message) {
		long chatId = message.getChatId();
		String mensajeRecibido = message.getText();
		
		Long userId = message.getFrom().getId();
	    String userName = message.getFrom().getUserName();
	    String firstName = message.getFrom().getFirstName();
	    String lastName = message.getFrom().getLastName();

		// Verificar si el mensaje es el comando /cmds
		if ("/cmds".equals(mensajeRecibido.trim())) {
			SendMessage mensajeRespuesta = new SendMessage();
			mensajeRespuesta.setChatId(chatId);

			mensajeRespuesta.setText(
					"¡Hola " + (userName != null ? "@" + userName : firstName) + "! ¿En qué puedo ayudarte?\n\n"
							+ "Utiliza estos comandos para generar usuarios falsos:\n" + "*!fake au →* Australia\n"
							+ "*!fake br →* Brasil\n" + "*!fake ca →* Canadá\n" + "*!fake es →* España\n"
							+ "*!fake mx →* México\n" + "*!fake tr →* Turquía\n" + "*!fake us →* Estados Unidos\n"
							+ "\nGenera usuarios de otros países hablando conmigo en privado. ¡Házmelo saber en @"
							+ "HirakoFakeAdressBot" + "!");

			mensajeRespuesta.setParseMode(ParseMode.MARKDOWN);

			try {
				execute(mensajeRespuesta);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		} else if ("!fake au".equals(mensajeRecibido.trim())) {
			String respuestaFakeAU = GeneratedUserAU.fetchDataFromApi();
			SendMessage mensajeRespuestaAU = new SendMessage();

			// Agregamos una linea nueva
			respuestaFakeAU += "\n-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -\n" + "*Request →* "
					+ (userName != null ? "@" + userName : firstName);

			mensajeRespuestaAU.setChatId(chatId);
			mensajeRespuestaAU.setText(respuestaFakeAU);
			mensajeRespuestaAU.setParseMode(ParseMode.MARKDOWN);

			try {
				execute(mensajeRespuestaAU);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		} else if ("!fake br".equals(mensajeRecibido.trim())) {
			String respuestaFakeBR = GeneratedUserBR.fetchDataFromApi();
			SendMessage mensajeRespuestaBR = new SendMessage();

			// Agregamos una linea nueva
			respuestaFakeBR += "\n-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -\n" + "*Request →* "
					+ (userName != null ? "@" + userName : firstName);

			mensajeRespuestaBR.setChatId(chatId);
			mensajeRespuestaBR.setText(respuestaFakeBR);
			mensajeRespuestaBR.setParseMode(ParseMode.MARKDOWN);

			try {
				execute(mensajeRespuestaBR);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		} else if ("!fake ca".equals(mensajeRecibido.trim())) {
			String respuestaFakeCA = GeneratedUserCA.fetchDataFromApi();
			SendMessage mensajeRespuestaCA = new SendMessage();

			// Agregamos una linea nueva
			respuestaFakeCA += "\n-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -\n" + "*Request →* "
					+ (userName != null ? "@" + userName : firstName);

			mensajeRespuestaCA.setChatId(chatId);
			mensajeRespuestaCA.setText(respuestaFakeCA);
			mensajeRespuestaCA.setParseMode(ParseMode.MARKDOWN);

			try {
				execute(mensajeRespuestaCA);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		} else if ("!fake es".equals(mensajeRecibido.trim())) {
			String respuestaFakeES = GeneratedUserES.fetchDataFromApi();
			SendMessage mensajeRespuestaES = new SendMessage();

			// Agregamos una linea nueva
			respuestaFakeES += "\n-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -\n" + "*Request →* "
					+ (userName != null ? "@" + userName : firstName);

			mensajeRespuestaES.setChatId(chatId);
			mensajeRespuestaES.setText(respuestaFakeES);
			mensajeRespuestaES.setParseMode(ParseMode.MARKDOWN);

			try {
				execute(mensajeRespuestaES);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		} else if ("!fake mx".equals(mensajeRecibido.trim())) {
			String respuestaFakeMX = GeneratedUserMX.fetchDataFromApi();
			SendMessage mensajeRespuestaMX = new SendMessage();

			// Agregamos una linea nueva
			respuestaFakeMX += "\n-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -\n" + "*Request →* "
					+ (userName != null ? "@" + userName : firstName);

			mensajeRespuestaMX.setChatId(chatId);
			mensajeRespuestaMX.setText(respuestaFakeMX);
			mensajeRespuestaMX.setParseMode(ParseMode.MARKDOWN);

			try {
				execute(mensajeRespuestaMX);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		} else if ("!fake us".equals(mensajeRecibido.trim())) {
			String respuestaFakeUS = GeneratedUserUS.fetchDataFromApi();
			SendMessage mensajeRespuestaUS = new SendMessage();

			// Agregamos una linea nueva
			respuestaFakeUS += "\n-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -\n" + "*Request →* "
					+ (userName != null ? "@" + userName : firstName);

			mensajeRespuestaUS.setChatId(chatId);
			mensajeRespuestaUS.setText(respuestaFakeUS);
			mensajeRespuestaUS.setParseMode(ParseMode.MARKDOWN);

			try {
				execute(mensajeRespuestaUS);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
			
		} else if (mensajeRecibido.trim().startsWith("!fake ")) {
		    SendMessage mensajeRespuesta = new SendMessage();
		    mensajeRespuesta.setChatId(chatId);

		    mensajeRespuesta.setText("El comando que has ingresado no es válido. Por favor, asegúrate de utilizar uno de los comandos disponibles:\n"
		            + "*!fake au* → Australia\n"
		            + "*!fake br* → Brasil\n"
		            + "*!fake ca* → Canadá\n"
		            + "*!fake es* → España\n"
		            + "*!fake mx* → México\n"
		            + "*!fake tr* → Turquía\n"
		            + "*!fake us* → Estados Unidos\n"
		            + "\nSi necesitas ayuda, escribe /cmds.");

		    mensajeRespuesta.setParseMode(ParseMode.MARKDOWN);

		    try {
		        execute(mensajeRespuesta);
		    } catch (TelegramApiException e) {
		        e.printStackTrace();
		    }
		} else {

		}
	}

	private void handleNewChatMembers(Message message) {
		List<User> newMembers = message.getNewChatMembers();
		long chatId = message.getChatId();

		for (User newMember : newMembers) {
			Long userId = newMember.getId();
			String userName = newMember.getUserName();
			String firstName = newMember.getFirstName();
			String lastName = newMember.getLastName();

			// Responder al usuario en el grupo
			SendMessage mensajeRegistrado = new SendMessage();
			mensajeRegistrado.setChatId(chatId);

			String nombreImagen = "static/imagenes/pxfuel.jpg";
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(nombreImagen);

			if (inputStream != null) {
				InputFile inputFile = new InputFile(inputStream, nombreImagen);

				SendPhoto sendPhoto = new SendPhoto();
				sendPhoto.setChatId(String.valueOf(chatId));
				sendPhoto.setPhoto(inputFile);

				// Construir el mensaje de bienvenida
				String caption = "¡Hola " + (userName != null ? "@" + userName : firstName)
						+ "! Bienvenido al grupo. Si necesitas ayuda o quieres ver los comandos disponibles, "
						+ "simplemente escribe /cmds. ¡Estoy aquí para ayudarte!";

				sendPhoto.setCaption(caption);

				try {
					execute(sendPhoto);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
				
			} else {
				// Manejo de caso en que el InputStream es nulo
				System.out.println("Error: InputStream es nulo para la imagen " + nombreImagen);

				// Si el InputStream es nulo, enviar solo el mensaje de texto
				mensajeRegistrado.setText("¡Hola " + (userName != null ? "@" + userName : firstName)
						+ "! Bienvenido al grupo. Si necesitas ayuda o quieres ver los comandos disponibles, "
						+ "simplemente escribe /cmds. ¡Estoy aquí para ayudarte!");

				try {
					execute(mensajeRegistrado);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}

		}
	}

	private void handleCallbackQuery(Update update, String callbackData) {
		CallbackQuery callbackQuery = update.getCallbackQuery();
		Long chatId = callbackQuery.getMessage().getChatId();
		Integer messageId = callbackQuery.getMessage().getMessageId();

		String Username = callbackQuery.getFrom().getUserName();
		String Name = callbackQuery.getFrom().getFirstName();
		String LastName = callbackQuery.getFrom().getLastName();

		// Configura el teclado
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

		// Agrega los botones con identificadores únicos
		List<InlineKeyboardButton> buttonsRowOne = new ArrayList<>();
		List<InlineKeyboardButton> buttonsRowTwo = new ArrayList<>();
		List<InlineKeyboardButton> buttonsRowThree = new ArrayList<>();
		List<InlineKeyboardButton> buttonsRowFour = new ArrayList<>();

		SendMessage respuesta = new SendMessage();
		respuesta.setChatId(chatId.toString());

		String newText = null;

		switch (callbackData) {
		
		case "comandos":
        	// Llama al método para obtener datos de la API
        	newText = "*Selecciona un país para generar usuario:*\n\n"
                    + "*AU*: Australia, *BR*: Brasil, *CA*: Canadá, *ES*: España\n"
                    + "*MX*: México, *US*: Estados Unidos.\n";

            buttonsRowOne.add(new MyInlineKeyboardButton("AU", "au"));
            buttonsRowOne.add(new MyInlineKeyboardButton("BR", "br"));
            buttonsRowOne.add(new MyInlineKeyboardButton("CA", "ca"));
            buttonsRowTwo.add(new MyInlineKeyboardButton("ES", "es"));
            buttonsRowTwo.add(new MyInlineKeyboardButton("MX", "mx"));
            buttonsRowTwo.add(new MyInlineKeyboardButton("US", "us"));
            buttonsRowThree.add(new MyInlineKeyboardButton("Atrás ◀️", "atras"));
            rowsInline.add(buttonsRowOne);
            rowsInline.add(buttonsRowTwo);
            rowsInline.add(buttonsRowThree);
            markupInline.setKeyboard(rowsInline);
            respuesta.setReplyMarkup(markupInline);
            break;
            
		case "ayuda":
        	
        	newText = "*¡Bienvenido a la sección de ayuda!*\n\n" +
                    "Si necesitas asistencia o tienes preguntas, por favor, contacta al soporte técnico:\n\n" +
                    "*Mensaje a Soporte Técnico 1:* @whoisxannie\n" +
                    "*Mensaje a Soporte Técnico 2:* @wkzxann\n\n" +
                    "Este bot está en constante mejora para brindarte una mejor experiencia. ¡Gracias por tu paciencia y apoyo!";
            
            buttonsRowOne.add(new MyInlineKeyboardButton("Atrás ◀️", "atras"));
            rowsInline.add(buttonsRowOne);
            markupInline.setKeyboard(rowsInline);
            respuesta.setReplyMarkup(markupInline);
            break;

		// Logica botones para generar usuario
		case "au":
			newText = GeneratedUserAU.fetchDataFromApi();

			// Crear botones
			buttonsRowOne.add(new MyInlineKeyboardButton("Volver a generar 🔄", "au"));
			rowsInline.add(buttonsRowOne);

			// Verificar si el mensaje proviene de un chat privado
			if (update.getCallbackQuery().getMessage().getChat().isUserChat()) {
				// Agregar el botón "Atrás" solo en un chat privado
				buttonsRowTwo.add(new MyInlineKeyboardButton("Atrás ◀️", "comandos"));
				rowsInline.add(buttonsRowTwo);
			}

			markupInline.setKeyboard(rowsInline);
			respuesta.setReplyMarkup(markupInline);

			break;
		case "br":
			newText = GeneratedUserBR.fetchDataFromApi();

			// Crear botones
			buttonsRowOne.add(new MyInlineKeyboardButton("Volver a generar 🔄", "br"));
			rowsInline.add(buttonsRowOne);

			// Verificar si el mensaje proviene de un chat privado
			if (update.getCallbackQuery().getMessage().getChat().isUserChat()) {
				// Agregar el botón "Atrás" solo en un chat privado
				buttonsRowTwo.add(new MyInlineKeyboardButton("Atrás ◀️", "comandos"));
				rowsInline.add(buttonsRowTwo);
			}

			markupInline.setKeyboard(rowsInline);
			respuesta.setReplyMarkup(markupInline);

			break;
		case "ca":
			newText = GeneratedUserCA.fetchDataFromApi();

			buttonsRowOne.add(new MyInlineKeyboardButton("Volver a generar 🔄", "ca"));
			rowsInline.add(buttonsRowOne);

			// Verificar si el mensaje proviene de un chat privado
			if (update.getCallbackQuery().getMessage().getChat().isUserChat()) {
				// Agregar el botón "Atrás" solo en un chat privado
				buttonsRowTwo.add(new MyInlineKeyboardButton("Atrás ◀️", "comandos"));
				rowsInline.add(buttonsRowTwo);
			}

			markupInline.setKeyboard(rowsInline);
			respuesta.setReplyMarkup(markupInline);
			break;

		case "es":
			newText = GeneratedUserES.fetchDataFromApi();

			buttonsRowOne.add(new MyInlineKeyboardButton("Volver a generar 🔄", "es"));
			rowsInline.add(buttonsRowOne);

			// Verificar si el mensaje proviene de un chat privado
			if (update.getCallbackQuery().getMessage().getChat().isUserChat()) {
				// Agregar el botón "Atrás" solo en un chat privado
				buttonsRowTwo.add(new MyInlineKeyboardButton("Atrás ◀️", "comandos"));
				rowsInline.add(buttonsRowTwo);
			}

			markupInline.setKeyboard(rowsInline);
			respuesta.setReplyMarkup(markupInline);
			break;

		case "mx":
			newText = GeneratedUserMX.fetchDataFromApi();

			buttonsRowOne.add(new MyInlineKeyboardButton("Volver a generar 🔄", "mx"));
			rowsInline.add(buttonsRowOne);

			// Verificar si el mensaje proviene de un chat privado
			if (update.getCallbackQuery().getMessage().getChat().isUserChat()) {
				// Agregar el botón "Atrás" solo en un chat privado
				buttonsRowTwo.add(new MyInlineKeyboardButton("Atrás ◀️", "comandos"));
				rowsInline.add(buttonsRowTwo);
			}

			markupInline.setKeyboard(rowsInline);
			respuesta.setReplyMarkup(markupInline);
			break;

		case "us":
			newText = GeneratedUserUS.fetchDataFromApi();

			buttonsRowOne.add(new MyInlineKeyboardButton("Volver a generar 🔄", "us"));
			rowsInline.add(buttonsRowOne);

			// Verificar si el mensaje proviene de un chat privado
			if (update.getCallbackQuery().getMessage().getChat().isUserChat()) {
				// Agregar el botón "Atrás" solo en un chat privado
				buttonsRowTwo.add(new MyInlineKeyboardButton("Atrás ◀️", "comandos"));
				rowsInline.add(buttonsRowTwo);
			}

			markupInline.setKeyboard(rowsInline);
			respuesta.setReplyMarkup(markupInline);
			break;
		case "atras":
			newText = "¡Hola " + (Username != null && !Username.isEmpty() ? Username : Name)
					+ "!, Bienvenido(a) a HirakoBot. ¿En qué puedo ayudarte hoy? Por favor, selecciona una opción a continuación:";

			// Primera Fila
			buttonsRowOne.add(new MyInlineKeyboardButton("Generar Usuario 👤", "comandos"));
			buttonsRowOne.add(new MyInlineKeyboardButton("Ayuda ❓", "ayuda"));

			// Agrega las filas al teclado
			rowsInline.add(buttonsRowOne);

			// Asigna el teclado al mensaje
			markupInline.setKeyboard(rowsInline);
			respuesta.setReplyMarkup(markupInline);
			break;
		}

		try {
			// Si hay un nuevo texto, edita el mensaje existente
			if (newText != null) {
				EditMessageText editMessageText = new EditMessageText();
				editMessageText.setChatId(chatId.toString());
				editMessageText.setMessageId(messageId);
				editMessageText.setText(newText);
				editMessageText.setParseMode(ParseMode.MARKDOWN);
				// Actualiza el teclado solo si es necesario
				if (respuesta.getReplyMarkup() != null) {
					editMessageText.setReplyMarkup((InlineKeyboardMarkup) respuesta.getReplyMarkup());
				}
				execute(editMessageText);
			}
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBotUsername() {

		return "HirakoAdressBot";
	}

	@Override
	public String getBotToken() {
		return "6522554179:AAHHJmoqGDKwtliDPSQf1PjsdCch4tUtNI0";
	}

}
