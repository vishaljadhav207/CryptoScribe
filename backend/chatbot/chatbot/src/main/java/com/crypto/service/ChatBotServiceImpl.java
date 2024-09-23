package com.crypto.service;

import com.crypto.dto.CoinDto;
import com.crypto.response.ApiResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class ChatBotServiceImpl implements ChatbotService {

    String GEMINI_API_KEY="AIzaSyCpzz-E4wyVRbWeIWYxhrM31iZm_OojUBU ";

    private double convertTodouble(Object value){
        if(value instanceof Integer){
            return ((Integer)value).doubleValue();
        } else if (value instanceof  Long) {
            return ((Long)value).doubleValue();
        } else if (value instanceof  Double) {
            return (Double)value;
        }
        else throw  new IllegalArgumentException("unsupported type" + value.getClass().getName());
    }

    public CoinDto makeApiRequest(String currencyName) throws Exception {
        String  url="https://api.coingecko.com/api/v3/coins/bitcoin";

        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers=new HttpHeaders();

        HttpEntity<String>entity=new HttpEntity<>(headers);
        ResponseEntity<Map>responseEntity=restTemplate.getForEntity(url,Map.class);
        Map<String,Object>responseBody=responseEntity.getBody();

        if(responseBody!=null){
            Map<String, Object> image=(Map<String,Object>)responseBody.get("image");
            Map<String, Object> marketData=(Map<String,Object>)responseBody.get("market_data");

            CoinDto coinDto=new CoinDto();
            coinDto.setId((String) responseBody.get("id"));
            coinDto.setName((String) responseBody.get("name"));
            coinDto.setSymbol((String)responseBody.get("symbol"));
            coinDto.setImage((String)image.get("large"));

            //marketdata
            coinDto.setCurrentPrice(convertTodouble(((Map<String,Object>)marketData.get("current_price")).get("usd")));
            coinDto.setMarketCap(convertTodouble(((Map<String,Object>)marketData.get("market_cap")).get("usd")));
            coinDto.setMarketCapRank(convertTodouble(marketData.get("market_cap_rank")));
            coinDto.setTotalVolume(convertTodouble(((Map<String,Object>)marketData.get("total_volume")).get("usd")));
            coinDto.setHigh24h(convertTodouble(((Map<String,Object>)marketData.get("high_24h")).get("usd")));
            coinDto.setLow24h(convertTodouble(((Map<String,Object>)marketData.get("low_24h")).get("usd")));
            coinDto.setPriceChange24h(convertTodouble((marketData.get("price_change_24h"))));
            coinDto.setPriceChangePercentage24h(convertTodouble((marketData.get("price_change_percentage_24h"))));
            coinDto.setMarketCapChange24h(convertTodouble((marketData.get("market_cap_change_24h"))));
            coinDto.setMarketCapChangePercentage24h(convertTodouble((marketData.get("market_cap_change_percentage_24h"))));
            coinDto.setCirculatingSupply(convertTodouble((marketData.get("circulating_supply"))));
            coinDto.setTotalSupply(convertTodouble((marketData.get("total_supply"))));

            
            return coinDto;
        }
        throw new Exception("coin not found");


    }

    @Override
    public ApiResponse getCoinDetails(String prompt) throws Exception {
        CoinDto coinDto=makeApiRequest(prompt);
        System.out.println("coin dto------------- "+ coinDto);
        return null;
    }

    @Override
    public String simpleChat(String prompt) {
        String GEMINI_API_URL="https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key="+GEMINI_API_KEY;
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody=new JSONObject()
                .put("contents",new JSONArray().put(new JSONObject()
                        .put("parts",new JSONArray()
                                .put(new JSONObject().put("text",prompt))))).toString();
        HttpEntity<String>requestEntity=new HttpEntity<>(requestBody,headers);

        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String>response=restTemplate.postForEntity(GEMINI_API_URL,requestEntity,String.class);

        return response.getBody() ;
    }
}
