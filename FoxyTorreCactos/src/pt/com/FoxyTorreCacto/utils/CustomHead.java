package pt.com.FoxyTorreCacto.utils;

import java.lang.reflect.Field;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class CustomHead {
	 public static ItemStack setSkull(String url) {
		    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		    if (url == null || url.isEmpty())
		      return skull; 
		    SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
		    GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		    byte[] encodedData = 
		      Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", new Object[] { url }).getBytes());
		    profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
		    Field profileField = null;
		    try {
		      profileField = skullMeta.getClass().getDeclaredField("profile");
		    } catch (NoSuchFieldException|SecurityException e) {
		      e.printStackTrace();
		    } 
		    profileField.setAccessible(true);
		    try {
		      profileField.set(skullMeta, profile);
		    } catch (IllegalArgumentException|IllegalAccessException e) {
		      e.printStackTrace();
		    } 
		    skull.setItemMeta((ItemMeta)skullMeta);
		    return skull;
		  }
}
