package ru.betterend.world.features;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import ru.betterend.BetterEnd;

public class EndFeature {
	private Feature<?> feature;
	private ConfiguredFeature<?, ?> featureConfigured;
	private GenerationStep.Feature featureStep;
	
	private EndFeature() {}
	
	public EndFeature(String name, Feature<DefaultFeatureConfig> feature, GenerationStep.Feature featureStep, ConfiguredFeature<?, ?> configuredFeature) {
		Identifier id = BetterEnd.getResId(name);
		this.featureStep = featureStep;
		this.feature = Registry.register(Registry.FEATURE, id, feature);
		this.featureConfigured = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, configuredFeature);
	}
	
	public EndFeature(String name, Feature<DefaultFeatureConfig> feature) {
		Identifier id = BetterEnd.getResId(name);
		this.featureStep = GenerationStep.Feature.VEGETAL_DECORATION;
		this.feature = Registry.register(Registry.FEATURE, id, feature);
		this.featureConfigured = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, feature.configure(FeatureConfig.DEFAULT).decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(100))));
	}
	
	public EndFeature(String name, Feature<DefaultFeatureConfig> feature, int density) {
		Identifier id = BetterEnd.getResId(name);
		this.featureStep = GenerationStep.Feature.VEGETAL_DECORATION;
		this.feature = Registry.register(Registry.FEATURE, id, feature);
		this.featureConfigured = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, feature.configure(FeatureConfig.DEFAULT).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeatRandomly(density));
		//return new EndFeature(name, feature, GenerationStep.Feature.VEGETAL_DECORATION, feature.configure(FeatureConfig.DEFAULT).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeatRandomly(4));
	}
	
	public static EndFeature makeRawGenFeature(String name, Feature<DefaultFeatureConfig> feature, int chance) {
		ConfiguredFeature<?, ?> configured = feature.configure(FeatureConfig.DEFAULT).decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(100)));
		return new EndFeature(name, feature, GenerationStep.Feature.RAW_GENERATION, configured);
	}
	
	public static EndFeature makeLakeFeature(String name, Feature<DefaultFeatureConfig> feature, int chance) {
		ConfiguredFeature<?, ?> configured = feature.configure(FeatureConfig.DEFAULT).decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(100)));
		return new EndFeature(name, feature, GenerationStep.Feature.LAKES, configured);
	}
	
	public static EndFeature makeOreFeature(String name, Block blockOre, int veins, int veinSize, int offset, int minY, int maxY) {
		EndFeature newFeature = new EndFeature();
		OreFeatureConfig featureConfig = new OreFeatureConfig(new BlockMatchRuleTest(Blocks.END_STONE), blockOre.getDefaultState(), veinSize);
		RangeDecoratorConfig rangeDecorator = new RangeDecoratorConfig(offset, minY, maxY);
		ConfiguredFeature<?, ?> oreFeature = Feature.ORE.configure(featureConfig)
				  .decorate(Decorator.RANGE.configure(rangeDecorator))
				  .spreadHorizontally()
				  .repeat(veins);
		newFeature.feature = Feature.ORE;
		newFeature.featureStep = GenerationStep.Feature.UNDERGROUND_ORES;
		newFeature.featureConfigured = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, BetterEnd.getResId(name), oreFeature);
		
		return newFeature;
	}
	
	public Feature<?> getFeature() {
		return feature;
	}

	public ConfiguredFeature<?, ?> getFeatureConfigured() {
		return featureConfigured;
	}

	public GenerationStep.Feature getFeatureStep() {
		return featureStep;
	}
}
