# FunNow

## Overview

FunNow is an Android mash-up application. As a one-stop solution to entertainment in cities, It offers users detailed information consist of new movies in theaters, various activities, weather and latest news headlines. Users can see the descriptions of specific object.

## Deployment

SDK: `Java1.8`

## Instruction

Once running the application, it will get the location automatically and fetch data based on the city where it stays. Users are also allowed to choose cities they are interested in and get related information.

### 3.1 New movies in theaters

This section shows new movies in theaters. Users can choose certain movie to get details of it.

By clicking button "NOW!", users can see the list of movie theaters nearby.

### 3.2 Activities near me

This section shows all kinds of activities in the city, like exhibitions, concerts and so on. Users can see descriptions including start and end time, fee, address and explicit introduction.

### 3.3 Weather

This section offers elaborate information including houly weather, suggestions, and forecast in following days.

### 3.4 Latest news

This section shows brief description of latest news. To get futher information, just click it and then page will jump to the source website.

## 4. APIs

### 4.1 Douban Movie (json)

Fetch new movies in theaters.

API: `http://api.douban.com/v2/movie/in_theaters`

### 4.2 Douban Event (json)

Fetch events in particular city.

API: `https://api.douban.com/v2/event/list?loc=shanghai&count=60`(example)

### 4.3 BaiduMap

Query specific places in a circle area.

API: `http://api.map.baidu.com/place/v2/search`

Param: `query`, `location`, `radius`, `page_size`, `output`...

### 4.4 HeWeather (json)

Fetch weather and suggestions.

API: `https://free-api.heweather.com/v5/`


### 4.5 Netease News (json)

Fetch latest news.

API: `http://c.3g.163.com/nc/article/list/T1467284926140/0-20.html`(example)


## 5. Implementation

### Data

All data are fetched from network.

- Retrofit or Volley for network requests.

- Gson for data model.

- Parse xml response.

- Glide for image loading.


### Third party libraries

- Glide
- Gson
- ButterKnife
- Retrofit
- Volley
- OkHttp