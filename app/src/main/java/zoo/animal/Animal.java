package zoo.animal;

public sealed interface Animal permits Mammal, Bird, Fish, Reptile {
    String name();
}