package com.envyful.api.reforged.pixelmon.config;

import com.google.common.collect.Sets;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.entities.pixelmon.specs.IsLegendarySpec;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.Set;

@ConfigSerializable
public class PokeSpecPricing implements Comparable<PokeSpecPricing> {

    private static final Set<String> PRIORITY_OPERATORS = Sets.newHashSet("+", "-");

    private String spec;
    private MathHandler minPrice;

    private transient PokemonSpec cachedSpec = null;

    public PokeSpecPricing(String spec, MathHandler minPrice) {
        this.spec = spec;
        this.minPrice = minPrice;
    }

    public PokeSpecPricing() {
    }

    public PokemonSpec getSpec() {
        if (this.cachedSpec == null) {
            this.cachedSpec = new PokemonSpec(this.spec);
        }

        return this.cachedSpec;
    }

    public double apply(double currentValue) {
        switch (this.minPrice.getType().toLowerCase()) {
            default : case "+" :
                return currentValue + this.minPrice.getValue();
            case "-" :
                return currentValue - this.minPrice.getValue();
            case "*" :
                return currentValue * this.minPrice.getValue();
            case "/" :
                return currentValue / Math.max(0.00001, this.minPrice.getValue());
        }
    }

    @Override
    public int compareTo(PokeSpecPricing o) {
        if (o.minPrice.getType().equalsIgnoreCase(this.minPrice.getType())) {
            return 0;
        }

        if (PRIORITY_OPERATORS.contains(this.minPrice.getType()) && PRIORITY_OPERATORS.contains(o.minPrice.getType())) {
            return 0;
        }

        if (PRIORITY_OPERATORS.contains(this.minPrice.getType())) {
            return -1;
        }

        return 1;
    }

    @Override
    public String toString() {
        return "PokeSpecPricing{" +
                "spec='" + spec + '\'' +
                ", minPrice=" + minPrice +
                ", cachedSpec=" + cachedSpec +
                '}';
    }

    @ConfigSerializable
    public static class MathHandler {

        private String type;
        private double value;

        public MathHandler(String type, double value) {
            this.type = type;
            this.value = value;
        }

        public MathHandler() {
        }

        public String getType() {
            return this.type;
        }

        public double getValue() {
            return this.value;
        }

        @Override
        public String toString() {
            return "MathHandler{" +
                    "type='" + type + '\'' +
                    ", value=" + value +
                    '}';
        }
    }
}
