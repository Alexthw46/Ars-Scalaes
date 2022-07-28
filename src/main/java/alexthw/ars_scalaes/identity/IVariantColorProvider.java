package alexthw.ars_scalaes.identity;

import com.hollingsworth.arsnouveau.api.client.IVariantTextureProvider;

public interface IVariantColorProvider extends IVariantTextureProvider {

    String getColor();

    void setColor(String color);
}
