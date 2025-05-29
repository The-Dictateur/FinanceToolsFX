package com.example;

import java.net.http.HttpClient;

import org.mockito.*;

public class FinnhubServiceTest {

    @Mock
    private HttpClient mockHttpClient;

    @InjectMocks
    private FinnhubService service;
    
}
