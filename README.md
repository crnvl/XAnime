![Java CI with Maven](https://github.com/angelsflyinhell/XAnime/workflows/Java%20CI%20with%20Maven/badge.svg)
# XAnime 
Easy-To-Use Java Library to browse Anime on the Web.

## How to use
Simply copy all classes from [here](https://github.com/angelsflyinhell/XAnime/tree/stable/src/main/java/Tools) and follow the examples and documentation below!
[Requires JSoup]
#### Maven Repository coming soon.

### Supported Sites
- [4anime.to](4anime.to)

# Code Snippets and Documentation
## Documentation
#### In-Code Docs coming soon.

### search()
```java
  Show.search(String anime);
  
  /*returns: List<String>
        Returns a List of URLs to all Titles available from the search.
          anime   = Name of a Series or Anime.
  */
```

### getTitle()
```java
  Show.getTitle(List<String> urls, int title);
  
  /*returns: List<String>
        Returns a List of URLs to all Episodes available.
          urls    = List of URLs. Best practice is getting this List with search().
          title   = Index of List urls.
  */
```
### getEpisode()
```java
  Show.getEpisode(List<String> urls, int episode);
  
  /*returns: String
        Returns a String with the URL of a specific episode.
          urls       = List of URLs. Best practice is getting this List with getTitle().
          episodes   = Index of List urls.
  */
```

### getVideoURL()
```java
  Show.getVideoURL(String url);
  
  /*returns: String
        Returns a String with the Video URL of a specific episode.
          url   = URL of Episode. Best practice is getting this List with Episode().
  */
```

### getVideoURLWithClass()
```java
  Show.getVideoURL(String url, String htmlClass);
  
  /*returns: String
        Returns a String with the Video URL of a specific episode.
          url       = URL of Episode. Best practice is getting this List with Episode().
          htmlClass = Class Name for HTML Tag.
  */
```

### getAnime()

```java
  Show.getAnime(String anime, int title, int episode);
  
  /*returns: String
        Returns a String with the Video URL of a specific episode.
          anime   = Name of a Series or Anime.
          title   = Selects one of the Search Results for String anime.
          episode = Number of the Episode.
  */
```

## Code Snippets
### Full Request with Input
```java
  public static void main(String[] args) throws IOException {
  
        Show.config("https://4anime.to/", "/?s=", "episodes range active", "#headerDIV_95");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Specify an Anime:");
        String s = br.readLine();

        List<String> titles = Show.search(s);
        for (int i = 0; i < titles.size(); i++) {
            System.out.println(i + "    |   " + titles.get(i));
        }

        System.out.println("Select a title by typing one of the available numbers:");
        s = br.readLine();

        List<String> episodes = Show.getTitle(titles, Integer.parseInt(s));
        for (int i = 0; i < episodes.size(); i++) {
            System.out.println(i + "    |   " + episodes.get((episodes.size() - i) - 1));
        }

        System.out.println("Select an episode by typing one of the available numbers:");
        s = br.readLine();

        String url = Show.getEpisode(episodes, Integer.parseInt(s));
        System.out.println("Ready to watch: " + FourAnime.getVideoURL(url));
    }
    
```
