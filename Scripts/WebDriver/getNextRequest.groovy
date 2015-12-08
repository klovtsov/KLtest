ReqId = args[0]
secPartOfReqId = ReqId.split('-')[1].toInteger()+1
result = "RequestID: 1-$secPartOfReqId aaa"
print(result)