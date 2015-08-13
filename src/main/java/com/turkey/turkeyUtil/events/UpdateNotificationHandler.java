package com.turkey.turkeyUtil.events;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import com.turkey.turkeyUtil.TurkeyUtil;
import com.turkey.turkeyUtil.UtilSettings;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class UpdateNotificationHandler
{
	boolean hasChecked = false;

	@SuppressWarnings("unused")
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		if (!TurkeyUtil.proxy.isClient() || hasChecked) { return; }

		hasChecked = true;

		if(TurkeyUtil.VERSION == "TUR_VER" + "_KEY")
		{
			event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "[TurkeyUtil] Dev Copy, How did you get this?"));
			return;
		}

		try
		{
			String[] data = getNotification("http://bit.ly/14ZUXpn", true);

			if(!UtilSettings.UpdateCheck)return;

			String version = data[0].trim();
			String currentversion = TurkeyUtil.VERSION;
			if(version.contains("_"))
			{
				version = version.substring(version.indexOf("_") + 1);
			}
			if(currentversion.contains("_"))
			{
				currentversion = currentversion.substring(currentversion.indexOf("_") + 1);
			}
			String link = data[1].trim();
			int verStat = compareVersions(currentversion, version);

			if (verStat == -1)
			{
				event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "-------------------------------------------"));
				event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "[TurkeyUtil] Version " + version + " of Turkey is now available!"));
				event.player.addChatMessage(new ChatComponentText("Download:"));
				event.player.addChatMessage(new ChatComponentText("" + EnumChatFormatting.BLUE + EnumChatFormatting.UNDERLINE + link));
				event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "-------------------------------------------"));
			}
			else if (verStat == 0)
			{
				event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "[TurkeyUtil] " + version + " is up to date"));
			}
			else if (verStat == 1)
			{
				event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "[TurkeyUtil] " + version + " is a dev build"));
			}
			else if (verStat == -2)
			{
				event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "[TurkeyUtil] An error has occured while checking the version for TurkeyUtil!"));
			}

		} catch (Exception e)
		{
			event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "[TurkeyUtil] A critical error has occured while checking the version for TurkeyUtil!"));
			e.printStackTrace();
			return;
		}
	}

	public int compareVersions(String ver1, String ver2)
	{
		int[] oldNum;
		int[] newNum;
		String[] oldNumString;
		String[] newNumString;

		try
		{
			oldNumString = ver1.split("\\.");
			newNumString = ver2.split("\\.");

			oldNum = new int[] { Integer.valueOf(oldNumString[0]), Integer.valueOf(oldNumString[1]), Integer.valueOf(oldNumString[2]) };
			newNum = new int[] { Integer.valueOf(newNumString[0]), Integer.valueOf(newNumString[1]), Integer.valueOf(newNumString[2]) };
		} catch (Exception e)
		{
			return -2;
		}

		for (int i = 0; i < 3; i++)
		{
			if (oldNum[i] < newNum[i])
			{
				return -1; // New version available
			}
			else if (oldNum[i] > newNum[i]) { return 1; // Debug version ahead
			// of release
			}
		}

		return 0;
	}

	private String[] getNotification(String link, boolean doRedirect) throws Exception
	{
		URL url = new URL(link);
		HttpURLConnection.setFollowRedirects(false);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(false);
		con.setReadTimeout(20000);
		con.setRequestProperty("Connection", "keep-alive");

		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:16.0) Gecko/20100101 Firefox/16.0");
		((HttpURLConnection) con).setRequestMethod("GET");
		con.setConnectTimeout(5000);
		BufferedInputStream in = new BufferedInputStream(con.getInputStream());
		int responseCode = con.getResponseCode();
		if (responseCode != HttpURLConnection.HTTP_OK && responseCode != HttpURLConnection.HTTP_MOVED_PERM)
		{
			System.out.println("Update request returned response code: " + responseCode + " " + con.getResponseMessage());
		}
		else if (responseCode == HttpURLConnection.HTTP_MOVED_PERM)
		{
			if (doRedirect)
			{
				try
				{
					return getNotification(con.getHeaderField("location"), false);
				} catch (Exception e)
				{
					throw e;
				}
			}
			else
			{
				throw new Exception();
			}
		}
		StringBuffer buffer = new StringBuffer();
		int chars_read;
		// int total = 0;
		while ((chars_read = in.read()) != -1)
		{
			char g = (char) chars_read;
			buffer.append(g);
		}
		final String page = buffer.toString();

		String[] pageSplit = page.split("\\n");

		return pageSplit;
	}
}