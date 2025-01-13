package com.springboot.blog.springboot_blog_rest_api;

import com.springboot.blog.springboot_blog_rest_api.entity.Role;
import com.springboot.blog.springboot_blog_rest_api.repository.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring boot Blog App Rest APIs",
				description = "Spring boot Blog App Rest APIs Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Tai Ngonheng",
						email = "heng68807@gmail.com",
						url = "https://me-portfolio-heng.vercel.app/"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring boot blog app Documentation Github",
				url = "https://github.com/TaiNgonheng/Real_time_blog_app"

		)
)
public class SpringbootBlogRestApiApplication implements CommandLineRunner {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		roleRepository.save(adminRole);

		Role userRole = new Role();
		userRole.setName("USER_ROLE");
		roleRepository.save(userRole);
	}
}
