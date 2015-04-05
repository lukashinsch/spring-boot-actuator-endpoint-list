package eu.hinsch.spring.boot.actuator.endpointlist;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Created by lh on 01/04/15.
 */
public class EndpointListEndpoint implements MvcEndpoint, ApplicationContextAware {

    private final Configuration freemarkerConfig;
    private ApplicationContext applicationContext;

    public EndpointListEndpoint() {
        freemarkerConfig = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
    }

    @RequestMapping("/")
    @ResponseBody
    public String list() throws IOException, TemplateException {

        Stream<String> endpoints = applicationContext.getBeansOfType(Endpoint.class)
                .values()
                .stream()
                .filter(Endpoint::isEnabled)
                .map(Endpoint::getId);

        Stream<String> mvcEndpoints = applicationContext.getBeansOfType(MvcEndpoint.class)
                .values()
                .stream()
                .map(MvcEndpoint::getPath)
                .filter(path -> (path != null) && (path.length() > 0))
                .map(path -> path.startsWith("/") ? path.substring(1) : path);

        List<String> allEndpoints = Stream.of(endpoints, mvcEndpoints)
                .flatMap(stream -> stream)
                .sorted(naturalOrder())
                .distinct()
                .collect(toList());

        Map<String,List<String>> model = new HashMap<>();
        model.put("endpoints", allEndpoints);
        return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfig.getTemplate("endpoints.ftl"), model);
    }

    @Override
    public String getPath() {
        return "";
    }

    @Override
    public boolean isSensitive() {
        return false;
    }

    @Override
    public Class<? extends Endpoint> getEndpointType() {
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
