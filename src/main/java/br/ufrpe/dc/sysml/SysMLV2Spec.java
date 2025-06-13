package br.ufrpe.dc.sysml;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.omg.sysml.interactive.SysMLInteractive;
import org.omg.sysml.interactive.SysMLInteractiveResult;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SysMLV2Spec {

    private SysMLInteractive sysml;
    private Namespace rootNamespace;
    private String baseFilePath;
    private String systemLibPath;

    // Construtor para uso nos testes (instancia manual)
    public SysMLV2Spec(String baseFilePath, String systemLibPath) {
        this.baseFilePath = baseFilePath;
        this.systemLibPath = systemLibPath;
        initialize();
    }

    // Construtor padrão para injeção do Spring via AppProperties
    @Autowired
    public SysMLV2Spec(AppProperties appProperties) {
        this(appProperties.getBaseFilePath(), appProperties.getSystemlibpath());
    }

    private void initialize() {
        if (systemLibPath == null || systemLibPath.isEmpty()) {
            throw new RuntimeException("Erro: A propriedade app.systemlibpath não foi definida.");
        }
        sysml = SysMLInteractive.getInstance();
        sysml.setVerbose(false);
        sysml.loadLibrary(systemLibPath);
        sysml.setApiBasePath("http://sysml2.intercax.com:9000");
    }

    // Método público para carregar um arquivo específico
    public void parseFile(String fileName) {
        String filePath = baseFilePath + "/" + fileName;
        try {
            String fileContent = Files.readString(Path.of(filePath), Charset.forName("UTF-8"));
            SysMLInteractiveResult result = sysml.process(fileContent);
            if (!result.hasErrors()) {
                Element root = result.getRootElement();
                if (root instanceof Namespace) {
                    rootNamespace = (Namespace) root;
                }
            } else {
                throw new RuntimeException("Erro ao processar o arquivo SysML.");
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException ao ler o arquivo: " + e.getMessage(), e);
        }
    }

    public Namespace getRootNamespace() {
        return rootNamespace;
    }
}
