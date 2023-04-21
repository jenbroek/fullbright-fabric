package com.github.jensbrks.fullbright.mixin;

import com.github.jensbrks.fullbright.FullbrightClientInitializer;
import java.util.Optional;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleOption.DoubleSliderCallbacks.class)
public class DoubleSliderCallbacksMixin {

	@Inject(method = "validate", at = @At("HEAD"), cancellable = true)
	private void validate(Double double_, CallbackInfoReturnable<Optional<Double>> cir) {
		cir.setReturnValue(double_ >= 0.0 ? Optional.of(double_) : Optional.empty());
	}

	@ModifyArg(
		method = "comp_675",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/serialization/Codec;doubleRange(DD)Lcom/mojang/serialization/Codec;"
		),
		index = 1
	)
	private double adjustMaxInclusive(double maxInclusive) {
		return FullbrightClientInitializer.FULLBRIGHT_GAMMA;
	}

}
