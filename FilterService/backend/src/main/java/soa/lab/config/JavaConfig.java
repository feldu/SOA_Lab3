package soa.lab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import soa.lab.service.FilterService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

@Configuration
public class JavaConfig {
    @Bean
    public Context context() throws NamingException {
        Properties jndiProps = new Properties();
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProps.put("jboss.naming.client.ejb.context", true);
        jndiProps.put(Context.PROVIDER_URL,
                "http-remoting://127.0.0.1:31512");
        return new InitialContext(jndiProps);
    }

    @Bean
    public FilterService filterService(Context context) throws NamingException {
        return (FilterService) context.lookup("ejb:/FilterService-EJB/FilterServiceImpl!soa.lab.service.FilterService");
    }
}
