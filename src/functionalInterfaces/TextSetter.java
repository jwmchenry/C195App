package functionalInterfaces;

/**
 * This functional interface is used for a lambda to set text for translation.
 */
@FunctionalInterface
public interface TextSetter {
    String setText(String t);
}
