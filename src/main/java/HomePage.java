import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.autocomplete.AutoCompleteTextField;
import com.googlecode.wicket.jquery.ui.template.IJQueryTemplate;

/**
 * @author Maxim Butov
 */
public class HomePage extends WebPage {

    /**
     * id for text field
     */
    public static final String TEXT_FIELD_ID = "textField";

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final WebMarkupContainer root = new WebMarkupContainer("root");
        add(root.setOutputMarkupId(true));

        root.add(createSimpleTextField().setVisible(false));

        root.add(new AjaxLink("action") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                // replacing simple text field with autocomplete
                root.addOrReplace(createAutoCompleteTextField().setVisible(true));
                // hide action link
                setVisible(false);
                // render root
                target.add(root);
            }
        });
    }

    /**
     * @return simple text field
     */
    protected Component createSimpleTextField() {
        return new TextField<String>(TEXT_FIELD_ID, Model.of(""));
    }

    /**
     * @return autocomplete text field with custom template
     */
    protected Component createAutoCompleteTextField() {
        return new AutoCompleteTextField<String>(TEXT_FIELD_ID, Model.of("")) {
            @Override
            protected List<String> getChoices(String input) {
                return Arrays.asList("choice 1", "choice 2", "choice 3");
            }

            @Override
            protected IJQueryTemplate newTemplate() {
                return new IJQueryTemplate() {
                    @Override
                    public String getText() {
                        return "<a>custom template for ${value}</a>";
                    }

                    @Override
                    public List<String> getTextProperties() {
                        return null;
                    }
                };
            }
        };
    }
}
