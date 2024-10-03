import React from 'react'

const PromptMessage = ({message,index}) => {
  return (
    <div className='px-3 py-4'>{message} - {index}</div>
  )
}

export default PromptMessage