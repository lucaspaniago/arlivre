package br.com.arlivre.web.security;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setUrl("jdbc:postgresql://localhost:5432/avaliacao");
		ds.setUsername("postgres");
		ds.setPassword("mundial191663");

		// Respeitar a ordem: login, senha, ativo
		auth.jdbcAuthentication()
				.dataSource(ds)
				.usersByUsernameQuery(
						"select login, senha, ativo from usuario where login=?")
				.authoritiesByUsernameQuery(
						"select u.login, p.descricao from usuario u, perfil p where u.perfil_id=p.id and login=?");
	}

	// Aqui vai ditar as regras
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/admin/**")
				.hasAuthority("Administrador").and().formLogin()
				.loginPage("/login.xhtml").permitAll()
				.loginProcessingUrl("/login.xhtml").permitAll()
				.usernameParameter("login").passwordParameter("senha")
				.defaultSuccessUrl("/admin/principal.xhtml")
				.failureUrl("/login.xhtml?erro=true").and().rememberMe()
				.key("remember-me").useSecureCookie(true).and().logout().logoutUrl("/logout")
				.logoutSuccessUrl("/login.xhtml").and().exceptionHandling();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	// @Order(1)
	// public static class AdministradorSecurityConfiguration extends
	// WebSecurityConfigurerAdapter {

	// // Aqui vai ditar as regras
	// @Override
	// protected void configure(final HttpSecurity http) throws Exception {
	// http.csrf().disable();
	// http.authorizeRequests().antMatchers("/admin/**")
	// .access("hasAuthority('Administrador')").and().formLogin()
	// .loginPage("/login.xhtml").permitAll()
	// .loginProcessingUrl("/login.xhtml").permitAll()
	// .usernameParameter("login").passwordParameter("senha")
	// .defaultSuccessUrl("/admin/formavaliacao.xhtml")
	// .failureUrl("/login.xhtml?erro=true").and().rememberMe()
	// .key("remember-me").useSecureCookie(true).and().logout()
	// .logoutSuccessUrl("/login.xhtml").and().exceptionHandling();
	// }

	// }

	// public static class AlunoSecurityConfiguration extends
	// WebSecurityConfigurerAdapter {

	// // Aqui vai ditar as regras
	// @Override
	// protected void configure(final HttpSecurity http) throws Exception {
	// http.csrf().disable();
	// http.authorizeRequests().antMatchers("/admin/**")
	// .access("hasAuthority('Administrador')").and().formLogin()
	// .loginPage("/login.xhtml").permitAll()
	// .loginProcessingUrl("/login.xhtml").permitAll()
	// .usernameParameter("login").passwordParameter("senha")
	// .defaultSuccessUrl("/admin/formavaliacao.xhtml")
	// .failureUrl("/login.xhtml?erro=true").and().rememberMe()
	// .key("remember-me").useSecureCookie(true).and().logout()
	// .logoutSuccessUrl("/login.xhtml").and().exceptionHandling();
	// }
	// }

}
