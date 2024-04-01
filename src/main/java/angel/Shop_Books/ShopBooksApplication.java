package angel.Shop_Books;

import angel.Shop_Books.Views.BookForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class ShopBooksApplication {

	public static void main(String[] args) {
		// no usamos la clase de spring por defecto
		//SpringApplication.run(ShopBooksApplication.class, args);
		ConfigurableApplicationContext contextSpring= new SpringApplicationBuilder(ShopBooksApplication.class)
				.headless(false)
				.web(WebApplicationType.NONE)
				.run(args);
		// Ejecutamos el formulario
		EventQueue.invokeLater(()-> {
			//Obtenemos el objeto desde nuestro form swing a traves de Spring
			BookForm bookForm= contextSpring.getBean(BookForm.class);
			bookForm.setVisible(true);
		});
	}

}
