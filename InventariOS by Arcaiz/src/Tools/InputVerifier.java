package Tools;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.text.JTextComponent;

public class InputVerifier {

    private List<Input> components;

    private static final String REGEX_NON_EMPTY = ".*\\s*.";
    private static final String REGEX_ONLY_NUMBERS = "^[0-9]+";
    private static final String REGEX_PRICE = "^[0-9]+\\.[0-9]{0,2}";

    public InputVerifier() {
        this.components = new ArrayList<>();
    }

    public boolean add(JTextComponent component, String validators) {
        return this.components.add(new Input(component, validators));
    }

    public Input getInput(String name) {
        return this.components.stream()
                .filter(input -> input.component.getName().equals(name))
                .collect(Collectors.toList())
                .get(0);
    }

    public String getAllErrors() {
        StringBuilder validationErrors = new StringBuilder();
        this.components.stream()
                .filter(input -> !input.isValid)
                .forEach(input -> input.getErrors()
                        .forEach(error -> validationErrors.append(error).append("\n")));

        return validationErrors.toString();
    }

    public boolean validate() {

        long invalids = this.components.stream()
                .map(this::checkInput)
                .filter(input -> !input.isValid)
                .count();
        return invalids == 0;
    }

    private Input checkInput(Input input) {

        String validators[] = input.validators.split("\\|");
        boolean hasError = false, isInvalid;
        input.errors = new ArrayList<>();

        for (String validtor : validators) {
            isInvalid = false;

            switch (validtor) {
                case "required":
                    if (!Pattern.matches(REGEX_NON_EMPTY, input.component.getText())) {
                        isInvalid = true;
                        input.errors.add(input.component.getName() + " no puede estar vacío");
                    }
                    break;
                case "number":
                    if (!Pattern.matches(REGEX_ONLY_NUMBERS, input.component.getText())) {
                        isInvalid = true;
                        input.errors.add(input.component.getName() + " solo debe tener números");
                    }
                    break;
                case "price":
                    if (!Pattern.matches(REGEX_PRICE, input.component.getText())) {
                        isInvalid = true;
                        input.errors.add(input.component.getName() + " solo debe tener números decimales");
                    }
                    break;
            }
            if (isInvalid) {
                input.component.setBackground(new Color(251, 189, 176));
                hasError = true;
            } else {
                input.component.setBackground(Color.white);
            }
        }
        input.isValid = !hasError;
        return input;
    }

    class Input {

        private JTextComponent component;
        private String validators;
        private boolean isValid;
        private List<String> errors;

        public Input() {
        }

        public Input(JTextComponent component, String validators) {
            this.component = component;
            this.validators = validators;
        }

        public boolean isValid() {
            return isValid;
        }

        public List<String> getErrors() {
            return errors;
        }
    }
}
