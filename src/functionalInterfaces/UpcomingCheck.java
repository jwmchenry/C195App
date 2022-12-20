package functionalInterfaces;

/**
 * This functional interface sets up a lambda expression that checks to see if there are any upcoming appointments.
 */
@FunctionalInterface
public interface UpcomingCheck {
    void checkAppointment();
}
