package eu.hinsch.spring.boot.actuator.endpointlist;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.toList;

/**
 * Created by lh on 01/04/15.
 */
@ConfigurationProperties(prefix = "endpoints.list", ignoreUnknownFields = true)
public class EndpointListEndpoint implements MvcEndpoint, ApplicationContextAware {

    private final Configuration freemarkerConfig;
    private boolean isSensitive = false;
    private ApplicationContext applicationContext;
    private List<String> excludes = new ArrayList<>();
    private String id;

    public EndpointListEndpoint() {
        freemarkerConfig = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
    }

    @RequestMapping
    public void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {
        response.sendRedirect(request.getRequestURL().toString() + "/");
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
                .filter(id -> !excludes.contains(id))
                .filter(id -> !id.equals(this.id))
                .sorted(naturalOrder())
                .distinct()
                .collect(toList());

        EndpointsModel model = new EndpointsModel();
        model.setEndpoints(allEndpoints);
        String baseLink = id != null ? "../" : "";
        model.setBaseLink(baseLink);
        return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfig.getTemplate("endpoints.ftl"), model);
    }

    @Override
    public String getPath() {
        return id != null ? "/" + id : "";
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean isSensitive() {
        return isSensitive;
    }

    public void setSensitive(boolean isSensitive) {
        this.isSensitive = isSensitive;
    }

    public List<String> getExcludes() {
        return excludes;
    }

    public void setExcludes(List<String> excludes) {
        this.excludes.addAll(excludes);
    }

    @Override
    public Class<? extends Endpoint> getEndpointType() {
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static class EndpointsModel {
        private List<String> endpoints;
        private String baseLink;

        public List<String> getEndpoints() {
            return endpoints;
        }

        public void setEndpoints(List<String> endpoints) {
            this.endpoints = endpoints;
        }

        public String getBaseLink() {
            return baseLink;
        }

        public void setBaseLink(String baseLink) {
            this.baseLink = baseLink;
        }
    }
}
