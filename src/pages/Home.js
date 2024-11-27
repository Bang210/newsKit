import React, { useEffect, useState } from 'react';

const Home = () => {
  const [keywords, setKeywords] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [createdTime, setCreatedTime] = useState(null);
  const [childKeywords, setChildKeywords] = useState({});

  const apiUrl = process.env.REACT_APP_CORE_API_BASE_URL;

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`${apiUrl}/keyword/dataRequest`, {
          method: 'POST',
          credentials: 'include',
        });
        const result = await response.json();

        if (result.resultCode === "200") {
          setKeywords(result.data.keywordList);
          setCreatedTime(result.data.createdTime);
        } else {
          setError(result.msg);
        }
      } catch (err) {
        setError('데이터를 가져오는 중 오류가 발생했습니다.');
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  useEffect(() => {
    // 첫 번째 키워드의 자식 키워드를 자동으로 불러오기
    if (keywords.length > 0) {
      fetchChildKeywords(keywords[0]);
    }
  }, [keywords]); // keywords가 변경될 때마다 실행

  const fetchChildKeywords = async (keyword) => {
    if (childKeywords[keyword]) return; // 이미 불러온 경우
    try {
      const response = await fetch(`${apiUrl}}/keyword/dataRequest/${keyword}`, {
        method: 'POST',
        credentials: 'include',
      });
      const result = await response.json();

      if (result.resultCode === "200") {
        setChildKeywords((prev) => ({ ...prev, [keyword]: result.data.keywordList }));
      } else {
        setError(result.msg);
      }
    } catch (err) {
      setError('키워드를 가져오는 중 오류가 발생했습니다.');
    }
  };

  const formatDateTime = (dateTimeString) => {
    const date = new Date(dateTimeString);
    const options = {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
      hour12: false, // 24시간 형식
    };
    const formattedDate = date.toLocaleString('ko-KR', options);
    return formattedDate.replace(/\/|,/g, ' ').replace(/\s+/g, ' ').trim(); // 형식 맞추기
  };

  if (loading) {
    return <p><span className="loading loading-dots loading-lg"></span>로딩 중...</p>;
  }

  if (error) {
    return <p>{error}</p>;
  }

  return (
    <div>
      <div className='p-5'>
        {createdTime && (
          <>
            <p className="text-2xl font-bold">{formatDateTime(createdTime)}</p>
            <p> 기준 가장 많이 언급된 뉴스 키워드입니다.</p>
          </>
        )}
      </div>
      <ul>
        {keywords.map((keyword, index) => (
          <li key={index}>
            <div className="collapse collapse-plus bg-base-200 my-3">
              <input
                type="radio"
                name="my-accordion-3"
                defaultChecked={index === 0}
                onClick={() => fetchChildKeywords(keyword)} // 키워드 클릭 시 자식 키워드 요청
              />
              <div className="collapse-title text-xl font-medium ml-3">
                <div className="badge badge-outline">{index + 1}</div> {keyword}
              </div>
              <div className="collapse-content">
                {childKeywords[keyword] ? (
                  <ul>
                    {childKeywords[keyword].map((childKeyword, idx) => (
                      <li key={idx} className='bg-gray-300 px-3 rounded-md py-1 ml-3 inline'>#{childKeyword}</li>
                    ))}
                  </ul>
                ) : (
                  <p onClick={() => fetchChildKeywords(keyword)}>클릭해서 불러오기</p>
                )}
                <div className='p-1 mt-7 ml-2 flex flex-col'>
                  <p role='button' className='btn btn-outline btn-sm w-40 mb-2 border border-[#67E703] text-[#67E703] hover:bg-[#1EC800] hover:text-white'
                    onClick={() => window.open(`https://search.naver.com/search.naver?where=news&query=${keyword}`, '_blank')}>네이버에서 확인하기</p>
                  <p role='button' className='btn btn-outline btn-sm w-40 mb-2 border border-[#E5665D] text-[#E5665D] hover:bg-[#E5665D] hover:text-white'
                    onClick={() => window.open(`https://search.daum.net/search?w=news&nil_search=btn&DA=NTB&enc=utf8&cluster=y&cluster_page=1&q=${keyword}`, '_blank')}>다음에서 확인하기</p>
                </div>
              </div>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Home;