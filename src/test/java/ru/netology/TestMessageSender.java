package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMessageSender {

    @Test
    public void testMessageSenderImplMockRus(){
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.<String>any())).thenReturn(new Location(null, Country.RUSSIA, null, 0));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Привет");

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        String message = messageSender.send(headers);

        Pattern pattern = Pattern.compile("[а-яА-Я\\d\\s\\p{Punct}]*");
        Matcher matcher = pattern.matcher(message);
        boolean result = matcher.matches();

        Assertions.assertTrue(result);

    }

    @Test
    public void testMessageSenderImplMockEng(){
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.<String>any())).thenReturn(new Location(null, Country.USA, null, 0));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Hello");

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.44.183.149");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        String message = messageSender.send(headers);

        Pattern pattern = Pattern.compile("[a-zA-Z\\d\\s\\p{Punct}]*");
        Matcher matcher = pattern.matcher(message);
        boolean result = matcher.matches();

        Assertions.assertTrue(result);

    }


    @Test
    public void testMessageSenderImplRus(){
        GeoService geoService = new GeoServiceImpl();
        LocalizationService localizationService = new LocalizationServiceImpl();

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        String message = messageSender.send(headers);

        Pattern pattern = Pattern.compile("[а-яА-Я\\d\\s\\p{Punct}]*");
        Matcher matcher = pattern.matcher(message);
        boolean result = matcher.matches();

        Assertions.assertTrue(result);

    }

    @Test
    public void testMessageSenderImplEng(){
        GeoService geoService = new GeoServiceImpl();
        LocalizationService localizationService = new LocalizationServiceImpl();

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.44.183.149");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        String message = messageSender.send(headers);

        Pattern pattern = Pattern.compile("[a-zA-Z\\d\\s\\p{Punct}]*");
        Matcher matcher = pattern.matcher(message);
        boolean result = matcher.matches();

        Assertions.assertTrue(result);

    }

    @Test
    public void testGeoServiceImplRus(){
        GeoService geoService = new GeoServiceImpl();

        Country result = geoService.byIp("172.123.12.19").getCountry();

        Country expect = Country.RUSSIA;

        Assertions.assertEquals(expect, result);

    }

    @Test
    public void testGeoServiceImplEng(){
        GeoService geoService = new GeoServiceImpl();

        Country result = geoService.byIp("96.44.183.149").getCountry();

        Country expect = Country.USA;

        Assertions.assertEquals(expect, result);

    }

    @Test
    public void testLocalizationServiceEng(){
        LocalizationService localizationService = new LocalizationServiceImpl();

        String result = localizationService.locale(Country.USA);

        String expect = "Welcome";

        Assertions.assertEquals(expect, result);

    }

    @Test
    public void testLocalizationServiceRus(){
        LocalizationService localizationService = new LocalizationServiceImpl();

        String result = localizationService.locale(Country.RUSSIA);

        String expect = "Добро пожаловать";

        Assertions.assertEquals(expect, result);

    }
}
