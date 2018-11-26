# paging lib

## 定义datasource

继承PageKeyedDataSource、ItemKeyedDataSource、PositionalDataSource
```
Use PageKeyedDataSource if pages you load embed next/previous keys. For example, if you're fetching social media posts from the network, you may need to pass a nextPage token from one load into a subsequent load.
Use ItemKeyedDataSource if you need to use data from item N to fetch item N+1. For example, if you're fetching threaded comments for a discussion app, you might need to pass the ID of the last comment to get the contents of the next comment.
Use PositionalDataSource if you need to fetch pages of data from any location you choose in your data store. This class supports requesting a set of data items beginning from whatever location you select. For example, the request might return the 20 data items beginning with location 1200
```

通常继承PositionalDataSource， 如果要使用Placeholder， 需要datasource返回totalCount