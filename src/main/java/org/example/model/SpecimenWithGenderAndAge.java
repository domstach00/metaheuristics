package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ThreadLocalRandom;

@Getter
public class SpecimenWithGenderAndAge extends Specimen implements Cloneable {
    @Setter
    private SexEnum sex;
    @Setter
    private Integer age;

    public SpecimenWithGenderAndAge(DataTTP config) {
        super(config);
        this.age = 0;
        this.sex = SexEnum.values()[ThreadLocalRandom.current().nextInt(SexEnum.values().length)];
    }

    public SpecimenWithGenderAndAge(DataTTP config, int startAge) {
        super(config, startAge);
        this.age = startAge;
        this.sex = SexEnum.values()[ThreadLocalRandom.current().nextInt(SexEnum.values().length)];
    }

    public SpecimenWithGenderAndAge(DataTTP config, SexEnum sexEnum) {
        super(config, sexEnum);
        this.age = 0;
        this.sex = sexEnum;
    }

    public SpecimenWithGenderAndAge(SpecimenWithGenderAndAge other) {
        super(other);
        this.age = other.age;
        this.sex = other.sex;
    }

    public void growUp() {
        age++;
    }

    @Override
    public SpecimenWithGenderAndAge clone() {
        return new SpecimenWithGenderAndAge(this);
    }
}
