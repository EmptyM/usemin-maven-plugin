package com.podbox.builder;

import com.google.common.base.Optional;
import org.apache.maven.plugin.logging.Log;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;

import static org.jsoup.Jsoup.parse;

public class CdnBuilder extends AbstractBuilder {

    public CdnBuilder(final Log log, final File sourceDirectory, final File targetDirectory, final String sourceEncoding) {
        super(null, log, sourceDirectory, targetDirectory, sourceEncoding);
    }

    @Override
    public String usemin(final String path, final String html) throws IOException {
        final Document document = parse(html, sourceEncoding);

        for (final Element element : document.select("link[data-cdn]")) {
            log.info("  " + element.attr("href") + "  ⟶  " + element.attr("data-cdn"));
            element.attr("href", element.attr("data-cdn")).removeAttr("data-cdn");
        }

        for (final Element element : document.select("script[data-cdn]")) {
            log.info("  " + element.attr("src") + "  ⟶  " + element.attr("data-cdn"));
            element.attr("src", element.attr("data-cdn")).removeAttr("data-cdn");
        }

        log.info("");
        return document
                .normalise()
                .outputSettings(document.outputSettings()
                                .indentAmount(2)
                )
                .outerHtml();
    }

    @Override
    protected Optional<String> compile(final String path, final Document resources) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String getReplacement(final String resourceName) {
        throw new UnsupportedOperationException();
    }

}
